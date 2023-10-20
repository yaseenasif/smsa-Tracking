import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from 'src/environments/environment';
import { Observable } from 'rxjs';
import { VehicleType } from 'src/app/model/VehicleType';

@Injectable({
  providedIn: 'root'
})
export class VehicleTypeService {
  constructor(private http:HttpClient) { }
  url=environment.baseurl;
  
  getALLVehicleType():Observable<VehicleType[]>{
    return this.http.get<VehicleType[]>(this.url.concat('/vehicle-type'));
  }

  getByIDVehicleType(id:number):Observable<VehicleType>{
    return this.http.get<VehicleType>(this.url.concat('/vehicle-type/',id.toString()));
  }

  updateVehicleTypeById(id:number,body:VehicleType):Observable<VehicleType>{
    return this.http.patch<VehicleType>(this.url.concat('/vehicle-type/',id.toString()),body);
  }

  addVehicleType(body:VehicleType):Observable<VehicleType>{
   return this.http.post<VehicleType>(this.url.concat('/vehicle-type'),body);
  }

  deleteVehicleTypeByID(id:number):Observable<VehicleType>{
    return this.http.delete<VehicleType>(this.url.concat('/vehicle-type/',id.toString()));
  }

}
