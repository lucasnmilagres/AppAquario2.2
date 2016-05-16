<?php
$conn = mysql_connect("localhost","username","password") or    die('Could not connect: ' . mysql_error());

mysql_select_db("CreeLed", $conn) or	die('Could not connect to specific database: ' . mysql_error());

$table=$_GET['AquariumCode'].'_devices';

$result=mysql_query("SELECT * FROM `$table`", $conn) or	die('Invalid query: ' . mysql_error());

$index=mysql_num_rows($result)+1;
$deviceCode=$_GET['DeviceCode'];

$sql = "INSERT INTO `creeled`.`$table`(`Index`, `DeviceCode`) VALUES ($index, '$deviceCode')";

$result = mysql_query($sql,$conn);

if($result)
{
	$emparray = 'Device added sucessfully!';
}
else
{
	$emparray = 'Invalid query: ' . mysql_error();
}

header('Content-type: application/json');
echo json_encode(array('emparray'=>$emparray));
 
mysql_close($conn);
?>
