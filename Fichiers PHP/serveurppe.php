<?php
	include "fonctions.php";
	include "class.pdogsb.inc.php";
	include "fct.inc.php";
	
   // contrôle de réception de paramètre
if(isset($_REQUEST["operation"])){
   
	/*
	Récupération du login de l'utilisateur de l'application
	Cela va servir à identifier le visiteur concerné par les frais
	*/
	$login = $_REQUEST["login"];	
   
    /*
	Récupération du mot de passe de l'utilisateur de l'application
	Cela va servir à identifier le visiteur concerné par les frais
	*/
	$motDePasse = $_REQUEST["motdepasse"];
	
	/*
	***************************
	*/
	$pdo = PdoGsb::getPdoGsb();
			
	/*
	Test pour voir si le login et mdp correspondent à un visiteur 
	*/
	
	$infosVisiteur = $pdo->getInfosVisiteur($login, $motDePasse);
	
	if (is_array($infosVisiteur)){
		 
		/*
		********
		*/
		$idVisiteur = $infosVisiteur['id'];
		  
		$nomVisiteur = $infosVisiteur['nom'];
		
		$prenomVisiteur = $infosVisiteur['prenom'];
		
			print ("synchronisation%");
			print ("synchronisation%");
			
			/*
			Récupération de l'année actuelle
			*/
			$anneeEnCour = date("Y");
						
			/*
			Récupération du mois actuel au format 1 à 12 sans les zéros initiaux
			*/
			$moisEnCourSansZero = date("n");
						
			/*
			Récupération du mois actuel au format 01 à 12 avec les zéros initiaux
			*/
			$moisEnCourAvecZero = date("m");
			
			$moisFormatyyyymm = $anneeEnCour.$moisEnCourAvecZero;
						
			/*
			Récupération des données concernants les frais forfaitaires
			et hors forfait
			*/
			$receptionBrutDesDonnees = $_REQUEST["lesdonnees"];	
			
			/*
			Decodage des données reçues et rangement dans un tableau array
			*/
			$lesDonnees = array(json_decode($receptionBrutDesDonnees, true));
			
			try{		
				/*
				 Récupère le nombre de mois ayant des données
				*/
				$nombreDeMoisDansDonnees = sizeof($lesDonnees[0]);
				
				/*
				***************************
				*/
				for ($k = 0; $k < $nombreDeMoisDansDonnees; $k++){
					
					if (($lesDonnees[0][$k]['annee'] == $anneeEnCour) && ($lesDonnees[0][$k]['mois'] == $moisEnCourSansZero)){
						
						$tableauFraisForfait['ETP'] = $lesDonnees[0][$k]['etape'];
						 
						$tableauFraisForfait['KM'] = $lesDonnees[0][$k]['km'];
						 
						$tableauFraisForfait['NUI'] = $lesDonnees[0][$k]['nuitee'];
						
						$tableauFraisForfait['REP'] = $lesDonnees[0][$k]['repas'];
						
						
						/*
						***************************
						*/
						$tailletableaufraishorsforfait = sizeof(($lesDonnees[0])[$k]['lesFraisHf']);			
						
						/*
						***************************
						*/
						for ($y = 0; $y < $tailletableaufraishorsforfait; $y++){				
							
							/*
							***************************
							*/
							$tableauFraisHorsForfait[$y]['jour'] = ($lesDonnees[0][$k]['lesFraisHf'][$y]['jour']);
							$tableauFraisHorsForfait[$y]['montant'] = ($lesDonnees[0][$k]['lesFraisHf'][$y]['montant']);
							$tableauFraisHorsForfait[$y]['motif'] = ($lesDonnees[0][$k]['lesFraisHf'][$y]['motif']);				
							
							
						}			
					}			
				}
				

				/*
				***************************
				*/	
				if($pdo->estPremierFraisMois($idVisiteur, $moisFormatyyyymm)){	
				
					/*
					***************************
					*/	
					$pdo->creeNouvellesLignesFrais($idVisiteur, $moisFormatyyyymm);		
				}
				
				/*
				***************************
				*/	
				if (lesQteFraisValides($tableauFraisForfait)) {
					
					/*
					***************************
					*/	
					$pdo->majFraisForfait($idVisiteur, $moisFormatyyyymm, $tableauFraisForfait);				
				}			
				
				/*
				***************************
				*/	
				foreach($tableauFraisHorsForfait as $ligne){					
					$jourFraisHorsForfaitun = ($ligne['jour'])."\r\n";
					$jourFraisHorsForfaitdeux = sprintf("%02d", $jourFraisHorsForfaitun);
					$dateFormater = $jourFraisHorsForfaitdeux."/".$moisEnCourAvecZero."/".$anneeEnCour;
					$dateFormaterEnAnglais = dateFrancaisVersAnglais($dateFormater);
					$montant = ($ligne['montant'])."\r\n";
					$motif = ($ligne['motif'])."\r\n";
					
					/*
					***************************
					*/	
					if(!$pdo->verifierSiFraisHorsForfaitExiste($idVisiteur, $moisFormatyyyymm, $motif, $dateFormaterEnAnglais, $montant)){
						
						/*
						***************************
						*/	
						$pdo->creeNouveauFraisHorsForfait($idVisiteur, $moisFormatyyyymm, $motif, $dateFormater, $montant);											
					}
				}
			}catch(PDOException $e){
				print "Erreur !%".$e->getMessage();
				die();
			}		
	}else{			
		print ("visiteurNonReconnu%");
		print ("visiteurNonReconnu%");					
	}
}
	

?>