<?php
	require_once '../src/models/BaseModel.php';
	require_once '../src/models/ModelOrder.php';
	require_once '../src/models/ModelUser.php';
	require_once '../src/models/ModelMaterial.php';

	class ModelDetailOrder extends BaseModel{
		public static function getFields(){
			return array(
				"uid", "nobukti", "tanggal", "order_id", "user_id", "odp", "qrcode", "status", "keterangan"
			);
		}

		//override
		public static function retrieve($id){
			$obj = parent::retrieve($id);
			if (isset($obj)) $obj->items = ModelDetailOrderMaterial::retrieveList('detailorder_id = '. $obj->id);
			return $obj;
		}

		public static function deleteFromDB($id){
			try{
				$obj = parent::retrieve($id);
				$str = static::generateSQLDelete("id=". $id);
				$str = $str . ModelDetailOrderMaterial::generateSQLDelete(
					'detailorder_id = '. $obj->id);
				DB::executeSQL($str);
			} catch (Exception $e) {
				$db->rollback();
				throw $e;
			}
		}

		public static function saveToDB($obj){
			$classname = get_called_class();
			$db = new DB();
			$db = $db->connect();
			$db->beginTransaction();
			try {
				if (!static::isNewTransaction($obj)){
					//id masih pakai id client, set pakai id server
					if (isset($obj->restclient)){
						if ($obj->restclient){
							static::setIDByUID($obj);
						}
					}
					$sql = ModelDetailOrderMaterial::generateSQLDelete('detailorder_id = '. $obj->id);
					$db->prepare($sql)->execute();
					// throw new Exception($sql, 1);
				}
				static::saveObjToDB($obj, $db);
				foreach($obj->items as $item){
					$item->detailorder_id = $obj->id;
					ModelDetailOrderMaterial::saveObjToDB($item, $db);
				}
				$db->commit();
				$db = null;

			} catch (Exception $e) {
				$db->rollback();
				throw $e;
			}
		}

		public static function prepareUpload($obj) {
			try{
				if (isset($obj->order_uid)) {
					$id = ModelOrder::getIDFromUID($obj->order_uid);
					if ($id>0) $obj->order_id = $id;
				}

				if (isset($obj->user_uid)) {
					$id = ModeUser::getIDFromUID($obj->user_uid);
					if ($id>0) $obj->user_id = $id;
				}

				foreach($obj->items as $item){
					ModelDetailOrderMaterial::prepareUpload($item);
				}

			} catch (Exception $e) {
				throw $e;
			}
		}
	}


	class ModelDetailOrderMaterial extends BaseModel{
		public static function getFields(){
			return array(
				"uid", "detailorder_id", "material_id", "qty", "serialnumber"
			);
		}

		public static function prepareUpload($obj) {
			if (!isset($obj->material_uid)) return;
			try{
				$id = ModelMaterial::getIDFromUID($obj->material_uid);
				if ($id>0) $obj->material_id = $id;
			} catch (Exception $e) {
				throw $e;
			}
		}
	}
