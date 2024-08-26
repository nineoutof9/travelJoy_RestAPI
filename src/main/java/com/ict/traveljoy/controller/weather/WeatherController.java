package com.ict.traveljoy.controller.weather;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ict.traveljoy.pushalarm.service.PushAlarmDTO;
import com.ict.traveljoy.weather.service.WeatherDTO;
import com.ict.traveljoy.weather.service.WeatherService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;



@Tag(name = "Weather", description = "날씨")
@RestController
@RequestMapping("/api")
@CrossOrigin
@RequiredArgsConstructor
public class WeatherController {
	
	@Autowired
	public final WeatherService weatherService;
	
	//날씨 저장
	@PostMapping("/weather")
	@Operation(summary = "날씨 저장", description = "날씨 저장 컨트롤러")
	public ResponseEntity<WeatherDTO> saveWeather(@RequestBody WeatherDTO weatherDTO){
		try {
			WeatherDTO saveDTO = weatherService.saveWeather(weatherDTO);
		return ResponseEntity.ok(saveDTO);
		}
		catch(Exception e) {e.printStackTrace();
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
		}
	}
	//날씨 수정
	   @PutMapping("/weather/{id}")
	   public ResponseEntity<WeatherDTO> updateRegion(@PathVariable("id") Long id, @RequestBody WeatherDTO weatherDTO) {
	       try {
	    	   WeatherDTO updatedRegion = weatherService.updateWeather(id, weatherDTO);
	           return new ResponseEntity<>(updatedRegion, HttpStatus.OK);
	       } catch (IllegalArgumentException e) {
	           return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	       } catch (Exception e) {
	           e.printStackTrace();
	           return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	       }
	   }
	
	//날씨 삭제
    @DeleteMapping("/weather/{id}")
    @Operation(summary = "날씨 삭제", description = "날씨 삭제 컨트롤러")
    public String deleteWeahter(@PathVariable("id") Long id) {
    	weatherService.deleteWeather(id);
       return "날씨 삭제 성공";
    }
	//날씨 조회
    @GetMapping("/weather/{id}")
	@Operation(summary = "날씨 조회", description = "날씨 조회 컨트롤러")
	public ResponseEntity<WeatherDTO> findWeatherById(@PathVariable("id")Long id){
		
		try {
		Optional<WeatherDTO> weatherId = weatherService.findWeatherById(id);
		if(weatherId.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(weatherId.get(),HttpStatus.OK);
		
		}catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
    //모든 날씨정보 조회
    @GetMapping("/weather/all")
    public ResponseEntity<List<WeatherDTO>> getAllWeathers() {
    	try {
    		List<WeatherDTO> weatherAll = weatherService.findAllWeathers();
    		if(weatherAll == null) {
    			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    		}
    		return new ResponseEntity<>(weatherAll,HttpStatus.OK);
    	}catch(Exception e) {
    		e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    	}
    }

}
