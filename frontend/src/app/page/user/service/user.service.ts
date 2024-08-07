import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { PaginatedResponse } from 'src/app/model/PaginatedResponse';
import { ResetPassword } from 'src/app/model/ResetPassword';
import { User } from 'src/app/model/User';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  constructor(private http:HttpClient) { }
  url=environment.baseurl;
  
  getAllUser(page:number,size:number,obj:any):Observable<PaginatedResponse<User>>{
    let queryParams = new HttpParams();
    queryParams = queryParams.append("value", obj ? JSON.stringify(obj) : '');
    queryParams = queryParams.append("page", page ? page : 0);
    queryParams = queryParams.append("size", size ? size : 10);
    return this.http.get<PaginatedResponse<User>>(this.url.concat('/filter-user'),{params:queryParams});
  }

  getInActiveUser():Observable<User[]>{
    return this.http.get<User[]>(this.url.concat('/inactive-user'));
  }
  // getUserByID(id:number):Observable<User>{
  //   return this.http.get<User>(this.url.concat('/user/',id.toString()));
  // }
  updateUserById(id:number,body:User):Observable<User>{
    
    return this.http.put<User>(this.url.concat('/edit-user/',id.toString()),body);
  }
  addUser(body:User):Observable<User>{
   return this.http.post<User>(this.url.concat('/user'),body);
  }
  deleteUserByID(id:number):Observable<User>{
    return this.http.delete<User>(this.url.concat('/delete-user/',id.toString()));
  }
  getUserById(id:number):Observable<User>{
    return this.http.get<User>(this.url.concat('/get-user/',id.toString()))
  }
  getAnyUserById(id:number):Observable<User>{
    return this.http.get<User>(this.url.concat('/get-any-user/',id.toString()))
  }
  getLoggedInUser():Observable<User>{
    return this.http.get<User>(this.url.concat('/get-loggedin/user'))
  }
  resetPassword(body:ResetPassword):Observable<ResetPassword>{
    return this.http.put<ResetPassword>(this.url.concat('/reset-password'),body);
  }
}
