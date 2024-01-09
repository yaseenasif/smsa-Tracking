import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Facility } from 'src/app/model/Facility';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class FacilityService {

  constructor(private http:HttpClient) { }
  url=environment.baseurl;

  addFacility(country:Facility):Observable<Facility>{
    return this.http.post<Facility>(this.url.concat('/add-facility'),country);

  }

  getAllFacility():Observable<Facility[]>{
    return this.http.get<Facility[]>(this.url.concat('/get-all'));
  }

  getFacilityByCountryID(country:number):Observable<Facility[]>{
    return this.http.get<Facility[]>(this.url.concat('/get-by-country/',country.toString()));
  }
  getFacilityById(id:number):Observable<Facility>{
    return this.http.get<Facility>(this.url.concat('/get-facility/',id.toString()));
  }

  updateFacility(id:number,country:Facility):Observable<Facility>{
    return this.http.put<Facility>(this.url.concat('/update-Facility/',id.toString()),country);
  }

  deleteFacility(id:number){
    return this.http.delete<any>(this.url.concat('/delete-Facility/',id.toString()));
  }
}
