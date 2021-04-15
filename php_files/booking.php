<?php

require "DataBase.php";
$db = new DataBase();

if (isset($_POST['username']) && isset($_POST['check_in']) && isset($_POST['check_out']) && isset($_POST['room_type_name'])) {
    if ($db->dbConnect()) {
    	$res = $db->book("bookings", "room", "user_book", $_POST['username'], $_POST['room_type_name'], $_POST['check_in'], $_POST['check_out']);
    	$split_res = explode(" ", $res);
    	if($split_res[0] == "success") {
    		echo "Room No is " . $split_res[1] . "----";
            echo "Amount to Pay: " . $split_res[2];
        }
        else if($split_res[0] == "error") {
          echo "No rooms available";
      }
      else echo "Error";
  }
  else echo "Database Connection Error";
} else echo "All fields are required";

?>