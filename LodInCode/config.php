<?php

$servername = "mysql2.gear.host";
$username = "autacc";
$password = "Gj1i_i?IOm5D";
$dbname = "autacc";

try {
    	$conn = new PDO("mysql:host=$servername;dbname=$dbname", $username, $password);
    	$conn->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
    }
catch(PDOException $e)
    {
    	die("OOPs something went wrong");
    }

?>
