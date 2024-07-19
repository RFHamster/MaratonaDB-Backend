package com.rfhamster.maratonaDB.vo;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;

import com.rfhamster.maratonaDB.model.Dicas;
import com.rfhamster.maratonaDB.model.Problema;
import com.rfhamster.maratonaDB.model.Solucao;
import com.rfhamster.maratonaDB.model.User;

public class CustomMapper {
	private static ModelMapper mapper = new ModelMapper();
	
	static {
		mapper.createTypeMap(User.class, UserSigninVO.class)
		.addMapping(User::getId, UserSigninVO::setKey);
		
		mapper.createTypeMap(UserSigninVO.class, User.class)
		.addMapping(UserSigninVO::getKey, User::setId);
		
		mapper.createTypeMap(Solucao.class, SolucaoVO.class)
		.addMapping(Solucao::getId, SolucaoVO::setKeySolucao);
		
		mapper.createTypeMap(SolucaoVO.class, Solucao.class)
		.addMapping(SolucaoVO::getKeySolucao, Solucao::setId);
		
		mapper.createTypeMap(Dicas.class, DicaVO.class)
		.addMapping(Dicas::getId, DicaVO::setKeyDica);
		
		mapper.createTypeMap(DicaVO.class, Dicas.class)
		.addMapping(DicaVO::getKeyDica, Dicas::setId);
		
		mapper.createTypeMap(Problema.class, ProblemaVO.class)
		.addMapping(Problema::getId, ProblemaVO::setKeyProblema);
		
		mapper.createTypeMap(ProblemaVO.class, Problema.class)
		.addMapping(ProblemaVO::getKeyProblema, Problema::setId);
	}
	
	public static <O, D> D parseObject(O origin, Class<D> destination) {
		return mapper.map(origin, destination);
	}
	
	public static <O, D> List<D> parseListObjects(List<O> origin, Class<D> destination) {
		List<D> destinationObjects = new ArrayList<D>();
		for (O o : origin) {
			destinationObjects.add(mapper.map(o, destination));
		}
		return destinationObjects;
	}
}
