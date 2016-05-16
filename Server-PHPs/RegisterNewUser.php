<?php
$conn = mysql_connect("localhost","username","password") or    die('Could not connect: ' . mysql_error());

mysql_select_db("CreeLed", $conn) or	die('Could not connect to specific database: ' . mysql_error());

$result=mysql_query("SELECT * FROM `users`", $conn) or	die('Invalid query: ' . mysql_error());

$index=mysql_num_rows($result)+1;
$userCode='US'.sprintf('%03d',$index);
$name=$_GET['Name'];
$email=$_GET['Email'];
$password=$_GET['Password'];
$accountType='Free';
$incomeDate=date('Y-m-d');
$tableName=$userCode.'_devices';

$sql = "\n"
    . " CREATE TABLE `creeled`.`$tableName` ( `Index` int( 11 ) NOT NULL AUTO_INCREMENT ,\n"
    . " `DeviceCode` varchar( 10 ) NOT NULL ,\n"
    . " PRIMARY KEY ( `Index` ) ,\n"
    . " UNIQUE KEY `DeviceCode` ( `DeviceCode` ) ) ENGINE = MyISAM DEFAULT CHARSET = latin1";

$result=mysql_query($sql, $conn) or	die('Invalid query: ' . mysql_error());

$sql = "INSERT INTO `creeled`.`users`(`Index`, `Usercode`, `Name`, `Email`, `Password`, `AccountType`, `IncomeDate`, `SelectedAquarium`) VALUES ($index, '$userCode', '$name', '$email', '$password', '$accountType', '$incomeDate', NULL)";

$result = mysql_query($sql,$conn);

 
if($result)
{
	$emparray = 'New user registered!';
}
else
{
	$emparray = 'Invalid query: ' . mysql_error();
}

header('Content-type: application/json');
echo json_encode(array('emparray'=>$emparray));
 
mysql_close($conn);
?>
