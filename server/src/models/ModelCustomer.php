<?php
	require_once '../src/models/BaseModel.php';
	require_once '../src/models/ModelArea.php';

	class ModelCustomer extends BaseModel{

		// public static function getTableName(){
		// 	return 'customer';
		// }


		public static function getFields(){
			return array(
				"uid", "nama", "alamat", "ktpno", "name", "custphone", "area_id", "phone", "inetnumber", "lng", "lat"
			);
		}

		public static function saveToDB($obj){
			$db = new DB();
			$db = $db->connect();
			$db->beginTransaction();
			try {
				static::saveObjToDB($obj, $db);
				$db->commit();
				$db = null;
			} catch (Exception $e) {
				$db->rollback();
				throw $e;
			}
		}

		public static function deleteFromDB($id){
			$str = static::generateSQLDelete("id=". $id);
			DB::executeSQL($str);
		}

		public static function retrieve($id){
			$obj = parent::retrieve($id);
			if (isset($obj->area_id)){
				$obj->area =  ModelArea::retrieve($obj->area_id);
			}
			return $obj;
		}

		public static function retrieveList($filter=''){
			$sql = 'select a.*, b.uid as area_uid from customer a inner join area b on a.area_id = b.id where 1=1 ';
			if ($filter<>''){
				$sql = $sql .' and '. $filter;
			}
			$obj = DB::openQuery($sql);
			return $obj;
		}
	}
