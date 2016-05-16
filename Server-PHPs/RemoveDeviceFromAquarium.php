<?php
$conn = mysql_connect("localhost","username","password") or    die('Could not connect: ' . mysql_error());

mysql_select_db("CreeLed", $conn) or	die('Could not connect to specific database: ' . mysql_error());

$table=$_GET['AquariumCode'].'_devices';
$deviceCode=$_GET['DeviceCode'];

$sql = "DELETE FROM `creeled`.`$table` WHERE `DeviceCode` LIKE '$deviceCode'";

$result = mysql_query($sql,$conn);

if($result)
{
	$emparray = 'Device removed sucessfully!';
}
else
{
	$emparray = 'Invalid query: ' . mysql_error();
}

header('Content-type: application/json');
echo json_encode(array('emparray'=>$emparray));
 
mysql_close($conn);
?>
