import { Component } from '@angular/core';
import { DomesticRoutesService } from '../service/domestic-routes.service';
import { MenuItem, MessageService } from 'primeng/api';
import { Routes } from '../../../model/ShipmentRoutes';

@Component({
  selector: 'app-get-domestic-routes',
  templateUrl: './get-domestic-routes.component.html',
  styleUrls: ['./get-domestic-routes.component.scss'],
  providers: [MessageService]
})
export class GetDomesticRoutesComponent {

  constructor(private domesticRouteService: DomesticRoutesService, private messageService: MessageService) { }

  items: MenuItem[] | undefined;
  domesticRoutes!: Routes[];
  visible: boolean = false;
  rID!: number
  first: number = 0;
  rows: number = 10;
  totalRecords: number = 0;
  myApiResponse: any;
  page = 0;
  size = 10;

  value: string = '';
  status:boolean=true


  ngOnInit() {
    this.items = [{ label: 'Domestic Route List' }];
    this.getAllDomesticRoutes(this.value.trim(),this.status,this.page,this.size);
  }
  searchByFilter(){
    this.getAllDomesticRoutes(this.value.trim(),this.status,0,this.size);
  }
  clearFilter(){
    this.value='';
  }
  onPageChange(event: any) {
    this.page = event.page;
    this.rows = event.rows;
    this.getAllDomesticRoutes(this.value.trim(),this.status,this.page,this.rows)
}

  getAllDomesticRoutes(value:string,status:boolean,page:number,size:number) {
    this.domesticRouteService.getAllDomesticRoutes({value:value,status:status},page,size).subscribe((res: any) => {
      this.domesticRoutes = res.content;
      this.myApiResponse = res;
      this.page=res.pageable.pageNumber;
      this.size=res.size;
      this.totalRecords = this.myApiResponse.totalElements;
    }, error => {
      this.messageService.add({ severity: 'error', summary: 'Error', detail: error.error.body });
    })
  }

  deleteDomesticRouteByID(id: number) {
    this.domesticRouteService.deleteDomesticRoute(id).subscribe((res: Error) => {
      this.visible = false;
      
      this.messageService.add({ severity: 'success', summary: 'Success', detail: 'Route is deleted on id ' + res.message });
      
      this.getAllDomesticRoutes(this.value,this.status,this.page,this.size);
    }, error => {
      this.messageService.add({ severity: 'error', summary: 'Error', detail: error.error.body });
    });
  }

  showDialog(id: number) {
    this.rID = id;
    this.visible = true;
  }

}
interface PageEvent {
  first: number;
  rows: number;
  page: number;
  pageCount: number;
}
