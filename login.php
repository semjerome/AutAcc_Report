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

$email = $_POST['email'];
$password = $_POST['password'];

$sql = "SELECT password FROM users Where email = '$email'";
$result = $conn->query($sql);

if ($result->num_rows > 0) {
     // output data of each row
     while($row = $result->fetch_assoc()) {
	 if($password == $row["password"]){
             echo "<br>Success";
         }
	 else{
	     echo "<br>Invalid Password";
	 }
     }

} else {
     echo "<br>Can not find an account";
}

$conn->close();
?>
  
<!-- checking
<html>
   <body>
   
      <form action = "<?php $_PHP_SELF ?>" method = "POST">
         EMAIL: <input type = "text" name = "email" />
         PASSWORD: <input type = "text" name = "password" />
         <input type = "submit" />
      </form>
   
   </body>
</html>
-->

</body>
</html>
