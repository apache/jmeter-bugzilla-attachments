// $Header$
/*
 * Copyright 2003-2004 The Apache Software Foundation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 */

package org.apache.jmeter.protocol.http.parser;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;

import org.apache.jorphan.logging.LoggingManager;
import org.apache.log.Logger;
import org.htmlparser.Node;
import org.htmlparser.Parser;
import org.htmlparser.Tag;
import org.htmlparser.tags.AppletTag;
import org.htmlparser.tags.BaseHrefTag;
import org.htmlparser.tags.BodyTag;
import org.htmlparser.tags.CompositeTag;
import org.htmlparser.tags.FrameTag;
import org.htmlparser.tags.ImageTag;
import org.htmlparser.tags.InputTag;
import org.htmlparser.tags.LinkTag;
import org.htmlparser.tags.ScriptTag;
import org.htmlparser.util.NodeIterator;
import org.htmlparser.util.ParserException;

/**
 * HtmlParser implementation using SourceForge's HtmlParser.
 * 
 * @version $Revision: 325588 $ updated on $Date: 2005-08-04 10:31:09 +0900 $
 */
class HtmlParserHTMLParser extends HTMLParser {
	/** Used to store the Logger (used for debug and error messages). */
	transient private static Logger log = LoggingManager.getLoggerForClass();

	protected HtmlParserHTMLParser() throws NoClassDefFoundError {
		super();
	}
	
    /** {@inheritDoc}. **/
	protected boolean isValid() {
		// check whether htmlparser exists.
		try {
			new Parser();
		} catch (NoClassDefFoundError e) {
			return false;
		}
		return true;
	}

	protected boolean isReusable() {
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.jmeter.protocol.http.parser.HtmlParser#getEmbeddedResourceURLs(byte[],
	 *      java.net.URL)
	 */
	public Iterator getEmbeddedResourceURLs(byte[] html, URL baseUrl, URLCollection urls) throws HTMLParseException {
        log.debug("Parsing html of: " + baseUrl);
        
		Parser htmlParser = null;
		try {
			String contents = new String(html);
			htmlParser = new Parser();
            htmlParser.setInputHTML(contents);
		} catch (Exception e) {
			throw new HTMLParseException(e);
		}

		// Now parse the DOM tree
		try {
			// we start to iterate through the elements
			parseNodes(htmlParser.elements(), baseUrl, urls);
			log.debug("End   : parseNodes");
		} catch (ParserException e) {
			throw new HTMLParseException(e);
		}

		return urls.iterator();
	}
    
    /**
     * Recursively parse all nodes to pick up all URL s.
     * @see e the nodes to be parsed
     * @see baseUrl Base URL from which the HTML code was obtained
     * @see urls URLCollection
     */
    private void parseNodes(final NodeIterator e,
            URL baseUrl, final URLCollection urls) 
        throws HTMLParseException, ParserException {
        while(e.hasMoreNodes()) {
            Node node = e.nextNode();
            // a url is always in a Tag.
            if (node instanceof Tag == false) {
                continue;
            }
            Tag tag = (Tag) node;
            String tagname=tag.getTagName();
            String binUrlStr = null;

            // first we check to see if body tag has a
            // background set
            if (tag instanceof BodyTag) {
                binUrlStr = tag.getAttribute("background");
            } else if (tag instanceof BaseHrefTag) {
                BaseHrefTag baseHref = (BaseHrefTag) tag;
                String baseref = baseHref.getBaseUrl().toString();
                try {
                    if (!baseref.equals(""))// Bugzilla 30713
                    {
                        baseUrl = new URL(baseUrl, baseHref.getBaseUrl() + "/");
                    }
                } catch (MalformedURLException e1) {
                    throw new HTMLParseException(e1);
                }
            } else if (tag instanceof ImageTag) {
                ImageTag image = (ImageTag) tag;
                binUrlStr = image.getImageURL();
            } else if (tag instanceof AppletTag) {
        		// look for applets

        		// This will only work with an Applet .class file.
        		// Ideally, this should be upgraded to work with Objects (IE)
        		// and archives (.jar and .zip) files as well.
                AppletTag applet = (AppletTag) tag;
                binUrlStr = applet.getAppletClass();
            } else if (tag instanceof InputTag) {
                // we check the input tag type for image
                String strType = tag.getAttribute("type");
                if (strType != null && strType.equalsIgnoreCase("image")) {
                    // then we need to download the binary
                    binUrlStr = tag.getAttribute("src");
                }
            } else if (tag instanceof LinkTag) {
                LinkTag link = (LinkTag) tag;
                if (link.getChild(0) instanceof ImageTag) {
                    ImageTag img = (ImageTag) link.getChild(0);
                    binUrlStr = img.getImageURL();
                }
            } else if (tag instanceof ScriptTag) {
                binUrlStr = tag.getAttribute("src");
            } else if (tag instanceof FrameTag) {
                binUrlStr = tag.getAttribute("src");
            } else if (tagname.equalsIgnoreCase("EMBED")
                || tagname.equalsIgnoreCase("BGSOUND")){
                binUrlStr = tag.getAttribute("src");  
            } else if (tagname.equalsIgnoreCase("LINK")) {
                if (tag.getAttribute("rel").equalsIgnoreCase("stylesheet")) {
                    binUrlStr = tag.getAttribute("href");
                }
            }

            if (binUrlStr != null) {
                urls.addURL(binUrlStr, baseUrl);
            }
            // second, if the tag was a composite tag,
            // recursively parse its children.
            if (tag instanceof CompositeTag) {
                CompositeTag composite = (CompositeTag) tag;
                parseNodes(composite.elements(), baseUrl, urls);
            }
        }
    }
}
