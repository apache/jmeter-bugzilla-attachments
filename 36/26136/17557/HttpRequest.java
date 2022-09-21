
import java.io.*;
import java.util.*;
import java.net.*;
import org.apache.commons.httpclient.*;
import org.apache.commons.httpclient.methods.*;


public class HttpRequest
extends TestProcedure {

    public TpHttpRequest()
    throws Exception {
    }

    public TpHttpRequest( String system )
    throws Exception {
        super( system );
    }

    // description for testsuite (short)
    public String _shortDescription = "TpHttpRequest makes a http request "	+
    "";

    // description for testsuite (long)
    public String _detailedDescription = "The class TpHttpRequest makes a "		+
    "http request and checks the return code.";

    public ParamStr  _targetIpAddress = new ParamStr("destination ip address", "139.21.93.123");

    public ParamStr  _targetProtocol = new ParamStr("protocol which is used to connect to the target",
    new String[] { "http", "https" });

    public ParamStr _targetPort = new ParamStr( " portnumber of target ", "80");

    public ParamStr _targetPath = new ParamStr( " url path of target (without leading slash)", "index.html");

    public ParamStr _sourceIpAddress = new ParamStr("list of local ip addresses, separated by semicolon or comma", "10.0.0.2,10.0.0.3,10.0.0.4,10.0.0.5,10.0.0.6,10.0.0.7");
    InetAddress _sourceIpAddressResolved = null;

    private static String className = TpHttpRequest.class.getName();

    private Vector _validIpAddresses = null;

    /**
     * composes the url from the given parameters of the procedure.
     * @return complete url
     */
    private String getURL()  throws Exception {
        String result = this._targetProtocol.getSubstitutedStrValue() +
        "://" +
        this._targetIpAddress.getSubstitutedStrValue() +
        ":" +
        this._targetPort.getSubstitutedStrValue() +
        "/" +
        this._targetPath.getSubstitutedStrValue();
        return  result;
    }

    private int getTargetPort() throws Exception  {
        String sPort = _targetPort.getSubstitutedStrValue();
        int port = Integer.parseInt(sPort);
        return port;

    }

    private HostConfiguration getHostConfiguration(InetAddress pAddress) throws Exception {
        HostConfiguration hc = new HostConfiguration();
        hc.setHost(this._targetIpAddress.getSubstitutedStrValue(),
        getTargetPort(),
        this._targetProtocol.getSubstitutedStrValue());
        hc.setLocalAddress(pAddress);
        return hc;
    }

    private List getSourceAddresses() throws Exception {
        if (_validIpAddresses == null) {
            String addresses = _sourceIpAddress.getSubstitutedStrValue();
            Vector result = new Vector();
            StringTokenizer tokenizer = new StringTokenizer(addresses, ";, ");
            while (tokenizer.hasMoreElements()) {
                String address = tokenizer.nextToken();
                InetAddress ia = null;
                try {
                    ia = InetAddress.getByName(address);
                } catch (UnknownHostException e) {
                    System.out.println( className + ".getSourceAddresses: address not valid: <" + address + ">");
                }
                if (ia != null) {
                    result.add(ia);
                }
            }
            _validIpAddresses = result;
        }
        return _validIpAddresses;

    }

    public void runTp() throws Exception{
        HttpMethod hMethod = new GetMethod();

        HttpClient hClient = new HttpClient();

        List sourceAddress = getSourceAddresses();
        if (sourceAddress.size() == 0) {
            _testProcedureResult.failed( "no source addresses available" );
        }
        Iterator ads = sourceAddress.iterator();
        while (ads.hasNext()) {
            InetAddress ia = (InetAddress) ads.next();
            HostConfiguration hc =  getHostConfiguration(ia);
            int statusCode = -1;
            try {
                // We will retry up to 3 times.
                for (int attempt = 0; statusCode == -1 && attempt < 3; attempt++) {
                    try {
                        hMethod.recycle();
                        hMethod.setPath(this._targetPath.getValue());
                        // execute the method.
                        statusCode = hClient.executeMethod(hc, hMethod);
                        _testProcedureResult.passed( "TpHttp passed" );
                    } catch (HttpRecoverableException e) {
                        System.out.println( className + ".runTp: A recoverable exception occurred, retrying.  " + e.getMessage());
                    }
                }
            } catch (BindException e) {
                _testProcedureResult.failed( "TpHttp: Address not valid " + ia.toString());
            } catch (IOException e) {
                _testProcedureResult.failed( "TpHttp failed" );
            }
        }

    }
}



