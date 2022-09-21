/*

 * ====================================================================

 * The Apache Software License, Version 1.1

 *

 * Copyright (c) 2001 The Apache Software Foundation.  All rights

 * reserved.

 *

 * Redistribution and use in source and binary forms, with or without

 * modification, are permitted provided that the following conditions

 * are met:

 *

 * 1. Redistributions of source code must retain the above copyright

 * notice, this list of conditions and the following disclaimer.

 *

 * 2. Redistributions in binary form must reproduce the above copyright

 * notice, this list of conditions and the following disclaimer in

 * the documentation and/or other materials provided with the

 * distribution.

 *

 * 3. The end-user documentation included with the redistribution,

 * if any, must include the following acknowledgment:

 * "This product includes software developed by the

 * Apache Software Foundation (http://www.apache.org/)."

 * Alternately, this acknowledgment may appear in the software itself,

 * if and wherever such third-party acknowledgments normally appear.

 *

 * 4. The names "Apache" and "Apache Software Foundation" and

 * "Apache JMeter" must not be used to endorse or promote products

 * derived from this software without prior written permission. For

 * written permission, please contact apache@apache.org.

 *

 * 5. Products derived from this software may not be called "Apache",

 * "Apache JMeter", nor may "Apache" appear in their name, without

 * prior written permission of the Apache Software Foundation.

 *

 * THIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESSED OR IMPLIED

 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES

 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE

 * DISCLAIMED.  IN NO EVENT SHALL THE APACHE SOFTWARE FOUNDATION OR

 * ITS CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,

 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT

 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF

 * USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND

 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,

 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT

 * OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF

 * SUCH DAMAGE.

 * ====================================================================

 *

 * This software consists of voluntary contributions made by many

 * individuals on behalf of the Apache Software Foundation.  For more

 * information on the Apache Software Foundation, please see

 * <http://www.apache.org/>.

 */

 package org.apache.jmeter.visualizers;



import org.apache.jmeter.gui.*;

import java.util.*;



import org.apache.jmeter.samplers.*;
import org.apache.jmeter.save.Saveable;
import org.apache.jmeter.util.JMeterUtils;


/**

 *  Title: Apache JMeter Description: Copyright: Copyright (c) 2000 Company:

 *  Apache Foundation

 *

 *@author     Michael Stover

 *@created    February 8, 2001

 *@version    1.0

 */



public class GraphModel implements JMeterComponentModel,SampleListener,Saveable,Clearable

{

	private String name;

	private List samples;

	private List listeners;



	private long averageSum = 0;

	private long variationSum = 0;

	private long counter = 0;

	private long previous = 0;

	private long max = 1;

	private boolean bigChange = false;

	private Sample current = new Sample(0,0,0);



	/**

	 *  Constructor for the GraphModel object

	 */

	public GraphModel()

	{

		listeners = new LinkedList();

		samples = Collections.synchronizedList(new LinkedList());

	}

	public void addJMeterComponent(JMeterComponentModel child)
	{
	}


	public void uncompile()

	{

	}

	public Class getTagHandlerClass()
	{
		return org.apache.jmeter.save.handlers.JMeterComponentHandler.class;
	}



	protected void fireDataChanged()

	{

		Iterator iter = listeners.iterator();

		if(bigChange)

		{

			while (iter.hasNext())

			{

				((ModelSupported)iter.next()).updateGui();

			}

			bigChange = false;

		}

		else

		{

			quickUpdate(current);

		}

	}



	protected void quickUpdate(Sample s)

	{

		Iterator iter = listeners.iterator();

		{

			while (iter.hasNext())

			{

				((GraphListener)iter.next()).updateGui(s);

			}

		}

	}



	public long getCurrentData()

	{

		return current.data;

	}



	public long getCurrentAverage()

	{

		return current.average;

	}



	public long getCurrentDeviation()

	{

		return current.deviation;

	}



	public int getSampleCount()

	{

		return samples.size();

	}



	public void addModelListener(java.awt.Component modelListener)

	{

		listeners.add(modelListener);

	}



	public List getSamples()

	{

		return samples;

	}



	/**

	 *  Sets the Name attribute of the GraphModel object

	 *

	 *@param  name  The new Name value

	 */

	public void setName(String name)

	{

		this.name = name;

	}



	/**

	 *  Gets the GuiClass attribute of the GraphModel object

	 *

	 *@return    The GuiClass value

	 */

	public Class getGuiClass()

	{

		return GraphVisualizer.class;

	}



	/**

	 *  Gets the Name attribute of the GraphModel object

	 *

	 *@return    The Name value

	 */

	public String getName()

	{

		return name;

	}



	/**

	 *  Gets the Editable attribute of the GraphModel object

	 *

	 *@return    The Editable value

	 */

	public boolean isEditable()

	{

		return false;

	}



	/**

	 *  Gets the AddList attribute of the GraphModel object

	 *

	 *@return    The AddList value

	 */

	public Collection getAddList()

	{

		return null;

	}



	/**

	 *  Gets the ClassLabel attribute of the GraphModel object

	 *

	 *@return    The ClassLabel value

	 */

	public String getClassLabel()

	{

		return JMeterUtils.getResString("graph_results_title");

	}



	public void sampleOccurred(SampleEvent e)

	{

		addNewSample(e.getResult().getTime());

		this.fireDataChanged();

	}



	public void sampleStarted(SampleEvent e)

	{

	}



	public long getMax()

	{

		return max;

	}



	public void clear()

	{

			samples.clear();

			averageSum = 0;

			variationSum = 0;

			counter = 0;

			previous = 0;

			max = 1;

			bigChange = true;

			current = new Sample(0,0,0);

		this.fireDataChanged();

	}



	private void addNewSample(long sample)

	{

			if (sample > max)

			{

			bigChange = true;

			max = sample;

			}

			long average = 0;
			long deviation = 0;
			if(sample != 0)
			{
				averageSum += sample;
				average = averageSum / ++counter;
				variationSum += Math.pow(sample - average, 2);
				deviation = (long) Math.pow(variationSum / counter, 0.5);
			}
			else
			{
				if (counter != 0)
				{
					average = averageSum / counter;
					deviation = (long) Math.pow(variationSum / counter, 0.5);
				}
				else
				{
					average = averageSum;
					deviation = (long) Math.pow(variationSum, 0.5);
				}
			}

			Sample s = new Sample(sample, average, deviation);

			previous = sample;

			current = s;

			samples.add(s);

	}



	public void sampleStopped(SampleEvent e)

	{

	}

}

