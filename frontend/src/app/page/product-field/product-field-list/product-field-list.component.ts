import { Component, OnInit } from '@angular/core';
import { ProductFieldServiceService } from '../service/product-field-service.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-product-field-list',
  templateUrl: './product-field-list.component.html',
  styleUrls: ['./product-field-list.component.scss']
})
export class ProductFieldListComponent implements OnInit {

  productFieldList?:any[];
  constructor(private productFieldServiceService:ProductFieldServiceService,
    private router:Router) { }

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

  edit(id: any) {
    this.router.navigate(['/add-product-field'], { queryParams: { id: id } });
  }

  remove(id:any){
    this.productFieldServiceService.removeProductField(id).subscribe((res:any)=>{
      console.log(res);
      this.getAllProductField();
    },(error:any)=>{
      console.log(error);
      
    })
  }
  
}
