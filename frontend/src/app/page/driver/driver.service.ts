import { Injectable } from '@angular/core';
import { HttpClient} from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class DriverService {

  constructor(private http:HttpClient) { }

  url:string=environment.baseurl;

  getDriver():Observable < any >{
    return this.http.get < any > (this.url+'/driver');
  }

  getByDriver(id:number):Observable < any >{
    return this.http.get < any > (this.url+'/driver/'+id);
  }

  addDriver(data:any):Observable < any >{
    return this.http.post < any > (this.url+'/driver',data);
  }

  deleteDriver(id:number):Observable < any >{
    return this.http.delete < any > (this.url+'/driver/'+id);
  }

  updateDriver(id:number,data:any):Observable < any >{
    return this.http.delete < any > (this.url+'/driver/'+id,data);
  }
  
}
