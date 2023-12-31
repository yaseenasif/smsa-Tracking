import { ProductField } from './../../../model/ProductField';
import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../../environments/environment'
import { ShipmentStatus } from 'src/app/model/ShipmentStatus';

@Injectable({
  providedIn: 'root'
})
export class ProductFieldServiceService {

  url: string = environment.baseurl;
  constructor(private httpClient: HttpClient) { }

  getAllProductFields(): Observable<any> {
    return this.httpClient.get<any>(`${this.url}/product-field-all`);
  }

  saveProductField(productField: any) {
    return this.httpClient.post<any>(`${this.url}/add-product-field`, productField);
  }

  getProductFieldById(id: any) {
    return this.httpClient.get<any>(`${this.url}/product-field/${id}`);
  }

  getProductFieldByName(name: string): Observable<ProductField> {
    return this.httpClient.get<ProductField>(`${this.url}/name/${name}`);
  }

  updateProductField(id: any, productField: any) {
    return this.httpClient.put<any>(`${this.url}/update-product-field/${id}`, productField);
  }

  removeProductField(id: any) {
    return this.httpClient.delete<any>(`${this.url}/delete-product-field/${id}`);
  }

  removeProductFieldValues(id: any, productFieldID: any) {
    return this.httpClient.delete<any>(`${this.url}/delete-product-field-value/${id}/${productFieldID}`)
  }
}

