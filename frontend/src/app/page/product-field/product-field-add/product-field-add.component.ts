import { Component, OnInit } from '@angular/core';
import { ProductFieldServiceService } from '../service/product-field-service.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-product-field-add',
  templateUrl: './product-field-add.component.html',
  styleUrls: ['./product-field-add.component.scss']
})
export class ProductFieldAddComponent implements OnInit {

  name:any;
  selectedDropdownType: any;
  makeProductField:Boolean=false;
  productFieldValues:any[]=[];

  dropdownTypes:any[]=["DROPDOWN","MULTISELECT"];
  constructor(private productFieldServiceService:ProductFieldServiceService,
              private router:Router         
    ) { }

  ngOnInit(): void {
  }

  formProductField(item:any){
    console.log(item);
    
  }

  addFormProducts(){
    console.log(this.name);
    console.log(this.selectedDropdownType);
    console.log(this.productFieldValues);
    
    this.productFieldServiceService
    .saveProductField({name:this.name,status:null,createdAt:null,type:this.selectedDropdownType,productFieldValuesList:this.productFieldValues})
    .subscribe((res:any)=>{
      console.log(res);
      this.router.navigate(['/product-field']);

      
    },(error:any)=>{
      console.log(error);
      
    })
  }
  type(){
    this.productFieldValues.push({name:null , status:null});
    this.makeProductField=true;
  }

  addProductFieldValues(){
    this.productFieldValues.push({name:null , status:null});
  }

  removeProductFieldValues(index:any){
    this.productFieldValues.splice(index,1)
  }
}
