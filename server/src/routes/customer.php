<?php
use \Psr\Http\Message\ServerRequestInterface as Request;
use \Psr\Http\Message\ResponseInterface as Response;

require_once '../src/models/ModelCustomer.php';
require_once '../src/classes/DB.php';


$app->get('/customer', function ($request, $response) {
	try{
		$list = ModelCustomer::retrieveList();
		return json_encode($list);
	}catch(Exception $e){
		$msg = $e->getMessage();
		return $response->withStatus(500)
			->withHeader('Content-Type', 'text/html')
			->write($msg);
	}
});


$app->get('/customer/{id}', function ($request, $response) {
	$id = $request->getAttribute('id');
	try{
		$list = ModelCustomer::retrieve($id);
		return json_encode($list);
	}catch(Exception $e){
		$msg = $e->getMessage();
		return $response->withStatus(500)
			->withHeader('Content-Type', 'text/html')
			->write($msg);
	}
});

$app->post('/customer', function ($request, $response) {
	$json = $request->getBody();
	$obj = json_decode($json);
	try{
		$str = ModelCustomer::saveToDB($obj);
		return json_encode($obj);
	}catch(Exception $e){
		$msg = $e->getMessage();
		return $response->withStatus(500)
			->withHeader('Content-Type', 'text/html')
			->write($msg);
	}
});


$app->delete('/customer/{id}', function (Request $request, Response $response) {
	$id = $request->getAttribute('id');
	try{
		ModelCustomer::deleteFromDB($id);
	}catch(Exception $e){
		$msg = $e->getMessage();
		return $response->withStatus(500)
			->withHeader('Content-Type', 'text/html')
			->write($msg);
	}
});

$app->post('/delete_customer/{id}', function (Request $request, Response $response) {
	$id = $request->getAttribute('id');
	try{
		ModelCustomer::deleteFromDB($id);
	}catch(Exception $e){
		$msg = $e->getMessage();
		return $response->withStatus(500)
			->withHeader('Content-Type', 'text/html')
			->write($msg);
	}
});

$app->get('/customer/{limit}/{page}/[{fieldname}/{keyword}]', function ($request, $response, $args) {
	try{
		$keyword = '';
		$fieldname = 'nama';
		if (isset($args['keyword'])) $keyword = $args['keyword'];
		if (isset($args['fieldname'])) $fieldname = $args['fieldname'];

		$sql = "select a.*, b.nama as area from customer a
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
