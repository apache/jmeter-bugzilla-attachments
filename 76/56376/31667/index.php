<?php
//Start the session
session_start();
class formKey
{
    //Here we store the generated form key
    private $formKey;
     
    //Here we store the old form key (more info at step 4)
    private $old_formKey;
     
    //The constructor stores the form key (if one excists) in our class variable
    function __construct()
    {
        //We need the previous key so we store it
        if(isset($_SESSION['form_key']))
        {
            $this->old_formKey = $_SESSION['form_key'];
        }
    }
 
    //Function to generate the form key
    private function generateKey()
    {
        //Get the IP-address of the user
        $ip = $_SERVER['REMOTE_ADDR'];
         
        //We use mt_rand() instead of rand() because it is better for generating random numbers.
        //We use 'true' to get a longer string.
        //See http://www.php.net/mt_rand for a precise description of the function and more examples.
        $uniqid = uniqid(mt_rand(), true);
         
        //Return the hash
        return md5($ip . $uniqid);
    }
 
     
    //Function to output the form key
    public function outputKey()
    {
        //Generate the key and store it inside the class
        $this->formKey = $this->generateKey();
        //Store the form key in the session
        $_SESSION['form_key'] = $this->formKey;
         
        //Output the form key
        echo "<input type='hidden' name='form_key' id='form_key' value='".$this->formKey."' />";
    }
 
     
    //Function that validated the form key POST data
    public function validate()
    {
        //We use the old formKey and not the new generated version
        if($_POST['form_key'] == $this->old_formKey)
        {
            //The key is valid, return true.
            return true;
        }
        else
        {
            //The key is invalid, return false.
            return false;
        }
    }
}
//Start the class
$formKey = new formKey();
 
$error = 'No error';
 
//Is request?
if($_SERVER['REQUEST_METHOD'] == 'post')
{
    //Validate the form key
    if(!isset($_POST['form_key']) || !$formKey->validate())
    {
        //Form key is invalid, show an error
        $error = 'Form key error!';
    }
    else
    {
        //Do the rest of your validation here
        $error = 'No form key error!';
    }
}
?>
     
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
    <meta http-equiv="content-type" content="text/html;charset=UTF-8" />
    <title>Securing forms with form keys</title>
</head>
<body>
    <div><?php if($error) { echo($error); } ?>
    <form action="" method="post">
    <dl>
        <?php $formKey->outputKey(); ?>
 
        <dt><label for="username">Username:</label></dt>
        <dd><input type="text" name="username" id="username" /></dd>
        <dt><label for="username">Password:</label></dt>
        <dd><input type="password" name="password" id="password" /></dd>
        <dt></dt>
        <dd><input type="submit" value="Submit" /></dd>
    <dl>
    </form>
</body>
</html>
