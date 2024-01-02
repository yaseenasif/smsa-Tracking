import { Component, OnInit } from '@angular/core';
import { ProductFieldServiceService } from '../service/product-field-service.service';
import { ActivatedRoute, Router } from '@angular/router';
import { MenuItem, MessageService } from 'primeng/api';

@Component({
  selector: 'app-product-field-add',
  templateUrl: './product-field-add.component.html',
  styleUrls: ['./product-field-add.component.scss'],
  providers: [MessageService]
})
export class ProductFieldAddComponent implements OnInit {

  typesDropDown: any = ["DROPDOWN", "MULTISELECT"]
  statusDropDown: any = ["Active", "Inactive"]
  typeValue: String = ''
  nameValue: String = ''
  statusValue: String = 'Active'
  pfvalueFlag: Boolean = false
  pfvaluesArray: any = []
  idFromQueryParam!: number
  fieldToUpdate: any = []
  buttonName: String = 'Add'
  visible!: boolean
  error: string = ''
  items: MenuItem[] | undefined;


  constructor(
    private productFieldServiceService: ProductFieldServiceService,
    private router: Router,
    private route: ActivatedRoute,
    private messageService: MessageService
  ) { }

  ngOnInit(): void {
    this.route.queryParams.subscribe(param => {
      this.idFromQueryParam = +param['id']
      if (Number.isNaN(this.idFromQueryParam)) {
        this.items = [{ label: 'Product Field List',routerLink:'/productFields'},{ label: 'Add Product Field'}];
        this.buttonName = 'Add'
      } else {
        this.items = [{ label: 'Product Field List',routerLink:'/productFields'},{ label: 'Update Product Field'}];
        this.buttonName = 'Update';
        this.productFieldServiceService.getProductFieldById(this.idFromQueryParam).subscribe(res => {
          this.fieldToUpdate = res
          if (this.fieldToUpdate.type == "MULTISELECT" || this.fieldToUpdate.type == "DROPDOWN") {
            this.pfvalueFlag = true
          }
          this.pfvaluesArray = this.fieldToUpdate.productFieldValuesList
          this.nameValue = this.fieldToUpdate.name
          this.statusValue = this.fieldToUpdate.status
          this.typeValue = this.fieldToUpdate.type
        }, error => {
          this.showError(error);
          this.visible = true;
        })
      }
    })
  }


  type() {
    if (this.typeValue == "DROPDOWN" || this.typeValue == "MULTISELECT") {
      if (Number.isNaN(this.idFromQueryParam)) {
        this.pfvaluesArray.length == 0 ? this.pfvaluesArray.push({ name: null, status: 'Active' }) : null;
      }
      debugger
      this.pfvalueFlag = true
    }
  }

  addpfvalues() {
    this.pfvaluesArray.push({ name: null, status: "Active" });
  }

  removeElement(i: number) {
    if (!Number.isNaN(this.idFromQueryParam)) {
      this.productFieldServiceService.removeProductFieldValues(this.idFromQueryParam, this.pfvaluesArray[i].id).subscribe(() => {
      }, error => {
        this.showError(error);
        this.visible = true;
      })
    }
    this.pfvaluesArray.splice(i, 1)
  }

  addProduct() {
    this.typeValue == "TEXTFIELD" || this.typeValue == "TOGGLE" ? this.pfvaluesArray = [] : null;
    // this.pfvaluesArray.forEach((element: any) => {
    //   element.name = element.name.split(' ');
    //   element.name = element.name.map((word: any) => word.toUpperCase()).join('_')
    // })
    let obj = {
      name: this.nameValue,
      status: this.statusValue,
      type: this.typeValue,
      productFieldValuesList: this.pfvaluesArray
    }
    if (Number.isNaN(this.idFromQueryParam)) {
      this.productFieldServiceService.saveProductField(obj).subscribe(() => {
        this.router.navigateByUrl('productFields')
      }, error => {
        this.showError(error);
        this.visible = true;
      })
    } else {
      this.productFieldServiceService.updateProductField(this.idFromQueryParam, obj).subscribe(() => {
        this.router.navigateByUrl('productFields')
      }, error => {
        this.showError(error);
        this.visible = true;
      })
    }
  }
  isTypeValueEmpty(): boolean {
    return !this.typeValue;
  }
  showError(error: any) {
    this.messageService.add({ severity: 'error', summary: 'Error', detail: error.error.error });
  }
}
