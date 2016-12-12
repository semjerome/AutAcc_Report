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
//$email = $_POST['email'];
$email = "myuz97@nate.com";

mysqli_set_charset($conn,"utf8"); 

$res = mysqli_query($conn,"SELECT ID, Time, Location, Video, Detail FROM blackbox Where email='$email'");  
   
$result = array();  
   
while($row = mysqli_fetch_array($res)){  
  array_push($result,  
    array('Id'=>$row[0],'Time'=>$row[1],'Video'=>$row[2],'Detail'=>$row[3]   
    ));  
}  
   
 
$json = json_encode(array("result"=>$result));
echo $json;
 
   
mysqli_close($con);  

?>  

</body>
</html>
