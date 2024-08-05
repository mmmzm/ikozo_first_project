/*
입력값이 비어있는지 확인하는 함수
value : 입력값
비어있으면 true, 그렇지 않으면 false
*/

let isEmpty = function(value) {

  if (null === value || value == undefined) {
    return true;
  }

  if (typeof value === 'string' && value.trim() === '') {
    return true;
  }

  if (Array.isArray(value) && value.length === 0) {
    return true;
  }

  return false;

}