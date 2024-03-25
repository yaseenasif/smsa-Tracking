import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from 'src/environments/environment';
import { Role } from 'src/app/model/Role';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class RoleService {

  constructor(private http:HttpClient) { }
  url=environment.baseurl;
  
  getALLRole():Observable<Role[]>{
    return this.http.get<Role[]>(this.url.concat('/roles'));
  }
  geRoleByID(id:number):Observable<Role>{
    return this.http.get<Role>(this.url.concat('/roles/',id.toString()));
  }
  
  assignPermissionToRole(body:Role):Observable<Role>{
   return this.http.post<Role>(this.url.concat('/assign-permission-to-role'),body);
  }

  addRole(body:Role):Observable<Role>{
    return this.http.post<Role>(this.url.concat('/role'),body);
   }
  

}
