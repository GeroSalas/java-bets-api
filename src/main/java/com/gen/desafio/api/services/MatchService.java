package com.gen.desafio.api.services;


import java.util.Date;
import java.util.List;
import org.springframework.stereotype.Service;
import com.gen.desafio.api.domain.dto.req.MatchReqDTO;
import com.gen.desafio.api.domain.model.Match;


@Service
public interface MatchService extends BaseService {
	
	 Match find(long id);
	 boolean updateMatch(MatchReqDTO match);
     List<Match> listAllMatchs();
     List<Match> getMatchsByDate(Date d);

}
