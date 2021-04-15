<?php

require "DataBase.php";
$db = new DataBase();

if (isset($_POST['username'])) {
	if ($db->dbConnect()) {
		$res = $db->readDet("users", $_POST['username']);
		echo $res;
	} else echo "Error: Database connection";
} else echo "Can't fetch username";

?>
