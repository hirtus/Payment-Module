const ListOrders = window['orders'];
const infoBlock = window['info-block'];
const searchBlock = window['search-block'];
let transactionBlock = window['transactions'];
let orderDTO;


const BASE_URL = window.location.href;
const SHOP_URL = `${BASE_URL}`;
const ORDER_URL = `${BASE_URL}`;

const FORM = window['form'];

const REG_URL = BASE_URL + 'registration-api';
const VALIDATION_SUCCESS = 'Validation Successful';
const MESSAGE_SUCCESS = 'Регистрация прошла успешно';

let csrf = document.getElementById('csrf').value;

