<?php
use \Psr\Http\Message\ServerRequestInterface as Request;
use \Psr\Http\Message\ResponseInterface as Response;

require_once '../src/models/ModelUser.php';
require_once '../src/classes/DB.php';


$app->post('/adminlogin', function ($request, $response) {
	$json = $request->getBody();

	$obj = json_decode($json);

	try{
		$obj = Modeluser::getUserLogin($obj->user_name, $obj->password, 'admin');
		return json_encode($obj);
	}catch(Exception $e){
		$msg = $e->getMessage();
		return $response->withStatus(500)
			->withHeader('Content-Type', 'text/html')
			->write($msg);
	}
});

$app->post('/teknisilogin', function ($request, $response) {
	$json = $request->getBody();
	$obj = json_decode($json);
	try{
		$obj = Modeluser::getUserLogin($obj->user_name, $obj->password, 'teknisi');
		return json_encode($obj);
	}catch(Exception $e){
		$msg = $e->getMessage();
		return $response->withStatus(500)
			->withHeader('Content-Type', 'text/html')
			->write($msg);
	}
});

$app->get('/user', function ($request, $response) {
	try{
		$list = Modeluser::retrieveList();
		return json_encode($list);
	}catch(Exception $e){
		$msg = $e->getMessage();
		return $response->withStatus(500)
			->withHeader('Content-Type', 'text/html')
			->write($msg);
	}
});


$app->get('/user/{id}', function ($request, $response) {
	$id = $request->getAttribute('id');
	try{
		$list = ModelUser::retrieve($id);
		return json_encode($list);
	}catch(Exception $e){
		$msg = $e->getMessage();
		return $response->withStatus(500)
			->withHeader('Content-Type', 'text/html')
			->write($msg);
	}
});

$app->post('/user', function ($request, $response) {
	$json = $request->getBody();
	$obj = json_decode($json);
	try{
		$str = ModelUser::saveToDB($obj);
		return json_encode($obj);
	}catch(Exception $e){
		$msg = $e->getMessage();
		return $response->withStatus(500)
			->withHeader('Content-Type', 'text/html')
			->write($msg);
	}
});


$app->delete('/user/{id}', function (Request $request, Response $response) {
	$id = $request->getAttribute('id');
	try{
		ModelUser::deleteFromDB($id);
	}catch(Exception $e){
		$msg = $e->getMessage();
		return $response->withStatus(500)
			->withHeader('Content-Type', 'text/html')
			->write($msg);
	}
});

$app->get('/user/{limit}/{page}/[{fieldname}/{keyword}]', function ($request, $response, $args) {
	try{
		$keyword = '';
		$fieldname = 'nama';
		if (isset($args['keyword'])) $keyword = $args['keyword'];
		if (isset($args['fieldname'])) $fieldname = $args['fieldname'];

		$sql = "select a.*, b.nama as area from users a
			left join area b on a.area_id=b.id";

		if ($fieldname == "area"){
			$fieldname = "b.nama";
		}else{
			$fieldname = "a.". $fieldname;
		}
		$sql = $sql ." where ".$fieldname." like '%" . $keyword ."%'";

		$data = DB::paginateQuery($sql, $args['limit'], $args['page']);
		return json_encode($data);
	}catch(Exception $e){
		$msg = $e->getMessage();
		return $response->withStatus(500)
			->withHeader('Content-Type', 'text/html')
			->write($msg);
	}
});
