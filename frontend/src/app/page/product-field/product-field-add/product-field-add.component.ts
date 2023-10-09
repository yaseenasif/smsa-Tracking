import { Component, OnInit } from '@angular/core';
import { ProductFieldServiceService } from '../service/product-field-service.service';
import { ActivatedRoute, Router } from '@angular/router';

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
  editId:any;
  heading:string='Add';
  buttonText:string='Submit';

  dropdownTypes:any[]=["DROPDOWN","MULTISELECT"];
  constructor(private productFieldServiceService:ProductFieldServiceService,
              private router:Router,
              private route: ActivatedRoute        
    ) { }

  ngOnInit(): void {
    this.route.queryParams.subscribe(params => {
      this.editId = params['id'];
    });
   
    if(this.editId == undefined){
      this.heading='Add';
      this.buttonText='Submit';
    }else{
      this.heading='Update';
      this.buttonText='Update';
      this.productFieldServiceService.getProductFieldById(this.editId).subscribe((res:any)=>{
        console.log(res);
        this.makeProductField=true;
        this.name=res.name;
        this.selectedDropdownType=res.type;
        this.productFieldValues=res.productFieldValuesList;
        
      },(error:any)=>{

      })
    }
  }

  formProductField(item:any){
    console.log(item);
    
  }

  addFormProducts(){
    console.log(this.name);
    console.log(this.selectedDropdownType);
    console.log(this.productFieldValues);

    let ProductFieldObj={name:this.name,status:null,createdAt:null,type:this.selectedDropdownType,productFieldValuesList:this.productFieldValues}
    
    if(this.editId == undefined){
      this.productFieldServiceService
      .saveProductField(ProductFieldObj)
      .subscribe((res:any)=>{
        console.log(res);
        this.router.navigate(['/product-field']);
      },(error:any)=>{
        console.log(error);
      })
    }else{
      this.productFieldServiceService.updateProductField(this.editId,ProductFieldObj).subscribe((res:any)=>{
        console.log(res);
        this.router.navigate(['/product-field']);
      },(error:any)=>{

      })
    }
    
  }
  type(){
    this.productFieldValues.push({name:null , status:null});
    this.makeProductField=true;
  }

  addProductFieldValues(){
    this.productFieldValues.push({name:null , status:null});
  }

  removeProductFieldValues(index:any){
    if(this.editId !=undefined){
      this.productFieldServiceService
      .removeProductFieldValues(this.editId,this.productFieldValues[index].id)
      .subscribe((res:any)=>{
        console.log(res);
        
      },(error:any)=>{
        console.log(error);
        
      })
    }
    this.productFieldValues.splice(index,1)
  }
}
