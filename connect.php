<?php
$email=$_POST['email'];
$password=$_POST['password'];

//connect to database
$conn= new mysqli('localhost','root','','librapp');
if($conn->connect_error){
  die('Connection Failed :' .$conn->connection_error);
}
else{
  $stmt=$conn->prepare("Insert into registration(email,password) values(?,?)");
  $stmt->bind_param("ss", $email,$password);
  $stmt->execute();
  echo("registration succesfull");
  $stmt->close();
  $conn->close();
}
?>
