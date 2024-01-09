import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Country } from 'src/app/model/Country';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class CountryService {

  constructor(private http:HttpClient) { }
  url=environment.baseurl;

  addCountry(country:Country):Observable<Country>{
    return this.http.post<Country>(this.url.concat('/add-country'),country);

  }

  getAllCountry():Observable<Country[]>{
    return this.http.get<Country[]>(this.url.concat('/all-country'));
  }

  getCountryById(id:number):Observable<Country>{
    return this.http.get<Country>(this.url.concat('/country/',id.toString()));
  }

  updateCountry(id:number,country:Country):Observable<Country>{
    return this.http.put<Country>(this.url.concat('/update-country/',id.toString()),country);
  }

  deleteCountry(id:number){
    return this.http.delete<any>(this.url.concat('/delete-country/',id.toString()));
  }
}
