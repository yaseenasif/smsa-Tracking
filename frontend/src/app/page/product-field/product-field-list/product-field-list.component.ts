import { Component, OnInit } from '@angular/core';
import { ProductFieldServiceService } from '../service/product-field-service.service';
import { Router } from '@angular/router';
import { MessageService } from 'primeng/api';
import { AuthguardService } from 'src/app/auth-service/authguard/authguard.service';

@Component({
  selector: 'app-product-field-list',
  templateUrl: './product-field-list.component.html',
  styleUrls: ['./product-field-list.component.scss'],
  providers: [MessageService]
})
export class ProductFieldListComponent implements OnInit {

  fields: any;
  visible: boolean = false;
  fID!: number;

  constructor(
    private productFieldServiceService: ProductFieldServiceService,
    private router: Router,
    private authguardService:AuthguardService,
    private messageService: MessageService) { }

  ngOnInit(): void {
    this.getAllProductField();
  }

  hasPermission(permission:string):boolean{
    return this.authguardService.hasPermission(permission)
  }

  getAllProductField() {
    this.productFieldServiceService.getAllProductFields().subscribe((res: any) => {
      this.fields = res;
    }, (error: any) => {


    })
  }

  deleteFieldByID(id: any) {
    this.productFieldServiceService.removeProductField(id).subscribe((res: any) => {
      this.visible = false;
      this.getAllProductField();
    }, (error: any) => {


    })
  }

  editProductField(fieldId:number){
    this.router.navigate(['/add-ProductField/fieldId'], { queryParams: { id: fieldId } });
  }

  showDialog(id: number) {
    this.fID = id;
    this.visible = true;
  }

}
