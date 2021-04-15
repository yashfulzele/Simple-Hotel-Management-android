<?php

require "DataBase.php";
$db = new DataBase();

if (isset($_POST['username']) && isset($_POST['service'])) {
	if ($db->dbConnect()) {
		if ($db->serviceBook("service", $_POST['username'], $_POST['service'])) {
			echo "Service Success";
		} else echo "Error: Can't perform the task";
	} else echo "Error: Database connection";
} else echo "All fields are required";

?>
