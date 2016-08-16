package com.github.enquete.repository;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.github.enquete.model.Establishment;
import com.github.enquete.model.EstablishmentCalculationVotes;

@Component
public class EstablishmentWinnerDao {
	
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
    public EstablishmentWinnerDao(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }  
      
    public List<EstablishmentCalculationVotes> resultByDate(Date begin, Date end) {                     
    	 StringBuilder sb = new StringBuilder();  
         
    	 sb.append("SELECT COUNT(estabelecimento.nome) AS  amountVoteByDate, ");  
         sb.append("votacao.data AS dateVote, ");
         sb.append("estabelecimento.nome As nameEstablishment, "); 
         sb.append("estabelecimento.id As idEstablishment, "); 
         sb.append("estabelecimento.telefone AS phoneEstablishment ");
         sb.append("FROM votacao ");
         sb.append("INNER JOIN estabelecimento ON votacao.estabelecimento_id = estabelecimento.id ");
         sb.append("WHERE votacao.data between ? and ? ");
         sb.append("GROUP BY dateVote, nameEstablishment, idEstablishment, phoneEstablishment ");
         sb.append("ORDER BY dateVote");

         SimpleDateFormat formatterDate = new SimpleDateFormat("yyyy/MM/dd", Locale.ENGLISH);
         Object[] paramenters = new Object[]{ formatterDate.format(begin) , formatterDate.format(end)};
         List<Map<String, Object>> list = jdbcTemplate.queryForList(sb.toString(), paramenters);  
         List<EstablishmentCalculationVotes>  establishmentCalculationVotesList = new ArrayList<EstablishmentCalculationVotes>();
         
         for (Map<String, Object> map : list) {                 
        	  EstablishmentCalculationVotes establishmentWinner = new EstablishmentCalculationVotes();  
        	  Establishment establishment = new Establishment();
        
        	  establishment.setId(Long.valueOf(map.get("idEstablishment").toString()));
        	  establishment.setName(map.get("nameEstablishment").toString());
        	  establishment.setPhone(map.get("phoneEstablishment").toString());
        	  establishmentWinner.setEstablishment(establishment);  
        	  establishmentWinner.setDate((Date)map.get("dateVote"));
        	  establishmentWinner.setVotesAmount(Integer.valueOf(map.get("amountVoteByDate").toString()));
        	  establishmentCalculationVotesList.add(establishmentWinner);  
         }  
         
         return establishmentCalculationVotesList;  
    } 
}
