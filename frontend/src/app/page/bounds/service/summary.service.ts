import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class SummaryService {
  url=environment.baseurl;
  constructor(private http:HttpClient) { }

  // getInboundSummary(obj:any,page:number,size:number):Observable<any>{
  //   return this.http
  // }


}
