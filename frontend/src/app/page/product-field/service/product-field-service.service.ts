import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import {environment} from '../../../../environments/environment'

@Injectable({
  providedIn: 'root'
})
export class ProductFieldServiceService {

  url:string=environment.baseurl;
  constructor(private httpClient: HttpClient) { }

  getAllProductFields():Observable < any >{
    return this.httpClient.get < any > (`${this.url}/product-field-all`);
  }

  saveProductField(productField:any){
    return this.httpClient.post<any> (`${this.url}/add-product-field`,productField);
  }

  getProductFieldById(id:any){
    return this.httpClient.get<any>(`${this.url}/product-field/${id}`);
  }

  updateProductField(id:any,productField:any){
    return this.httpClient.put<any> (`${this.url}/update-product-field/${id}`,productField);
  }
}

