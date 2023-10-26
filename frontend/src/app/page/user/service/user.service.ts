import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { User } from 'src/app/model/User';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  constructor(private http:HttpClient) { }
  url=environment.baseurl;
  
  getAllUser():Observable<User[]>{
    return this.http.get<User[]>(this.url.concat('/all-user'));
  }
  // getUserByID(id:number):Observable<User>{
  //   return this.http.get<User>(this.url.concat('/user/',id.toString()));
  // }
  updateUserById(id:number,body:User):Observable<User>{
    return this.http.put<User>(this.url.concat('/user/',id.toString()),body);
  }
  addUser(body:User):Observable<User>{
   return this.http.post<User>(this.url.concat('/user'),body);
  }
  deleteUserByID(id:number):Observable<User>{
    return this.http.delete<User>(this.url.concat('/delete-user/',id.toString()));
  }
}
