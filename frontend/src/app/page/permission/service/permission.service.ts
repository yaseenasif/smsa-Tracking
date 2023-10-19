import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from 'src/environments/environment';
import { Permission } from 'src/app/model/Permission';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class PermissionService {

  constructor(private http:HttpClient) { }
  url=environment.baseurl;
  
  getALLPermission():Observable<Permission[]>{
    return this.http.get<Permission[]>(this.url.concat('/permission'));
  }
  getByIDPermission(id:number):Observable<Permission>{
    return this.http.get<Permission>(this.url.concat('/permission/',id.toString()));
  }
  updatePermissionById(id:number,body:Permission):Observable<Permission>{
    return this.http.patch<Permission>(this.url.concat('/permission/',id.toString()),body);
  }
  addPermission(body:Permission):Observable<Permission>{
   return this.http.post<Permission>(this.url.concat('/permission'),body);
  }
  deletePermissionByID(id:number):Observable<Permission>{
    return this.http.delete<Permission>(this.url.concat('/permission/',id.toString()));
  }
}
