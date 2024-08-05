package com.pcwk.ehr.cmn;

import java.util.List;

public interface WorkDiv<T> extends PLog{
	/*
	 *   __   _  _    _  _  _  _     ___   __  ____    _  _   __   __    _  _    _  _   __   __    _  _ 
 /  \ / )( \  ( \/ )( \/ )   / __) /  \(    \  / )( \ /  \ (  )  ( \/ )  ( \/ ) /  \ (  )  ( \/ )
(  O )) __ (  / \/ \ )  /   ( (_ \(  O )) D (  ) __ ((  O )/ (_/\ )  /   / \/ \(  O )/ (_/\ )  / 
 \__/ \_)(_/  \_)(_/(__/     \___/ \__/(____/  \_)(_/ \__/ \____/(__/    \_)(_/ \__/ \____/(__/  
	 * *** 회원관리 프로그램 ***
	 * 1. 회원 목록 조회
	 * 2. 회원 단건 조회
	 * 3. 회원 단건 저장
	 * 4. 회원 수정
	 * 5. 회원 삭제
	 * 6. 종료
	 * */
	
	/*
	 * 	목록 조회
	 * @param search
	 * @return List<T>
	 * */
	public List<T> doRetrieve(DTO search);
	
	/**
	 * 단건 저장
	 * @param param
	 * @return 성공(1)/실패(0)
	 */
	public int doSave(T param);
	
	/*
	 * 단건 수정
	 * @param param
	 * @return 성공(1)/실패(0)
	 * */
	public int doUpdate(T param);
	
	/*
	 * 단건 삭제
	 * @param param
	 * @return 성공(1)/실패(0)
	 * */
	public int doDelete(T param);
	
	/*
	 * 단건 조회
	 * @param param
	 * @return T
	 * */
	public T doSelectOne(T param);
	
	/**
	 * 객체를 파일(json) 저장
	 * @return 저장건수
	 * */
	public int doSaveFile();
	
	/*
	 * 파일(json) 객체로 변환
	 * @return 저장건수
	 * */
	public int doReadFile();

} // WorkDiv
