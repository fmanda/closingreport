<?php
use \Psr\Http\Message\ServerRequestInterface as Request;
use \Psr\Http\Message\ResponseInterface as Response;

require_once '../src/models/ModelProduct.php';
require_once '../src/classes/DB.php';


$app->get('/product/{id}', function ($request, $response) {
	$id = $request->getAttribute('id');
	try{
		$list = ModelProduct::retrieveList();
		return json_encode($list);
	}catch(Exception $e){
		$msg = $e->getMessage();
		return $response->withStatus(500)
			->withHeader('Content-Type', 'text/html')
			->write($msg);
	}
});

$app->post('/product', function ($request, $response) {
	$json = $request->getBody();
	$obj = json_decode($json);
	try{
		$str = ModelProduct::saveToDB($obj);
		return json_encode($obj);
	}catch(Exception $e){
		$msg = $e->getMessage();
		return $response->withStatus(500)
			->withHeader('Content-Type', 'text/html')
			->write($msg);
	}
});

$app->delete('/product/{id}', function (Request $request, Response $response) {
	$id = $request->getAttribute('id');
	try{
		ModelProduct::deleteFromDB($id);
	}catch(Exception $e){
		$msg = $e->getMessage();
		return $response->withStatus(500)
			->withHeader('Content-Type', 'text/html')
			->write($msg);
	}
});
