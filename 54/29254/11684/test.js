var sharedURI = "http://localhost/shared";

function func1(doc) 
{
    
    var img = "<img style='float: right;' src='" + sharedURI + "/images/menu_arrow.gif'/>  ";
    doc.write("<link rel=stylesheet type='text/css' href='" + func2() + "'>\n");      	 
}

function func2()
{
    return "junk.css";
}