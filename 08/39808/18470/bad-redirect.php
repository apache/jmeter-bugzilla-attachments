<?php
// Delay a few seconds
sleep(5);
// Send an invalid redirect ("PORT" is invalid)
header("Location: http://HOST:PORT/");
exit;
?> 