<?php
$conn = mysql_connect("localhost","username","password") or    die('Could not connect: ' . mysql_error());

mysql_select_db("CreeLed", $conn) or	die('Could not connect to specific database: ' . mysql_error());

$table='registered_devices';
$deviceCode=$_GET['DeviceCode'];

$sql = "SELECT * FROM `$table` WHERE `DeviceCode` LIKE '$deviceCode'";

$result = mysql_query($sql,$conn) or die('Invalid query: ' . mysql_error());
 
//create an array
    $emparray = array();
    while($row =mysql_fetch_assoc($result))
    {
        $emparray[] = array('row'=>$row);
    }

header('Content-type: application/json');
echo json_encode(array('emparray'=>$emparray));

 
mysql_close($conn);
?>
