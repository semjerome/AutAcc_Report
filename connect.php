<!DOCTYPE html>
<html>
<body>

<?php
$servername = "mysql2.gear.host";
$username = "autacc";
$password = "Gj1i_i?IOm5D";
$dbname = "autacc";

// Create connection
$conn = new mysqli($servername, $username, $password, $dbname);
// Check connection
if ($conn->connect_error) {
     die("Connection failed: " . $conn->connect_error);
}

//$email=_GET["email"];

$sql = "SELECT password FROM users Where email='$_GET[email]'";
$result = $conn->query($sql);

if ($result->num_rows > 0) {
     // output data of each row
     while($row = $result->fetch_assoc()){ 
	print(json_encode($row));
     }
} else {
     echo "0 results";
}

$conn->close();

?>

</body>
</html>
