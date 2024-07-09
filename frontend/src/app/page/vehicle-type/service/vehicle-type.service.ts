import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from 'src/environments/environment';
import { Observable } from 'rxjs';
import { Vehicle} from 'src/app/model/VehicleType';

@Injectable({
  providedIn: 'root'
})
export class VehicleTypeService {
  constructor(private http:HttpClient) { }
  url=environment.baseurl;
  
  getALLVehicleType():Observable<Vehicle[]>{
    return this.http.get<Vehicle[]>(this.url.concat('/vehicle-type'));
  }

  getByIDVehicleType(id:number):Observable<Vehicle>{
    return this.http.get<Vehicle>(this.url.concat('/vehicle-type/',id.toString()));
  }

  updateVehicleTypeById(id:number,body:Vehicle):Observable<Vehicle>{
    console.log(body);
    
    return this.http.patch<Vehicle>(this.url.concat('/vehicle-type/',id.toString()),body);
  }

  addVehicleType(body:Vehicle):Observable<Vehicle>{
   return this.http.post<Vehicle>(this.url.concat('/vehicle-type'),body);
  }

  deleteVehicleTypeByID(id:number):Observable<Vehicle>{
    return this.http.delete<Vehicle>(this.url.concat('/vehicle-type/',id.toString()));
  }

}
