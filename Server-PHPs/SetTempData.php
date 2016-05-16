<?php
$conn = mysql_connect("localhost","username","password") or    die('Could not connect: ' . mysql_error());

mysql_select_db("CreeLed", $conn) or	die('Could not connect to specific database: ' . mysql_error());

$table=$_GET['DeviceCode'].'_temp_status';

$result=mysql_query("SELECT * FROM `$table`", $conn) or	die('Invalid query: ' . mysql_error());

$index=mysql_num_rows($result)+1;
$date=date('Y-m-d');
$time=date('H:i:s');
$minSet=$_GET['MinSet'];
$maxSet=$_GET['MaxSet'];
$curTemp=$_GET['CurrentTemp'];

$sql = "INSERT INTO `creeled`.`$table`(`Index`, `Date`, `Time`, `MinSet`, `MaxSet`, `CurrentTemp`) VALUES ($index, '$date', '$time', '$minSet', '$maxSet', '$curTemp')";

$result = mysql_query($sql,$conn);

if($result)
{
	$emparray = 'New settings registered!';
}
else
{
	$emparray = 'Invalid query: ' . mysql_error();
}

header('Content-type: application/json');
echo json_encode(array('emparray'=>$emparray));
 
mysql_close($conn);
?>
