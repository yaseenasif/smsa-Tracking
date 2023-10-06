import { Component, OnInit } from '@angular/core';
import { ProductFieldServiceService } from '../service/product-field-service.service';

@Component({
  selector: 'app-product-field-list',
  templateUrl: './product-field-list.component.html',
  styleUrls: ['./product-field-list.component.scss']
})
export class ProductFieldListComponent implements OnInit {

  productFieldList?:any[];
  constructor(private productFieldServiceService:ProductFieldServiceService) { }

  ngOnInit(): void {
    this.getAllProductField();
  }

  getAllProductField(){
    this.productFieldServiceService.getAllProductFields().subscribe((res:any)=>{
      this.productFieldList=res;
    },(error:any)=>{
      console.log(error);
      
    })
  }

}
