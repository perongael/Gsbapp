<?php
    function connexionPDO(){
        $login="root";
        $mdp="";
        $bd="test";
        $serveur="localhost";
        try {
            $conn = new PDO("mysql:host=$serveur;dbname=$bd", $login, $mdp);
            return $conn;
        } catch (PDOException $e) {
            print "Erreur de connexion PDO ";
            die();
        }
    }
?>
