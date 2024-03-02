import { InternationalRouteService } from './../../service/international-route.service';
import { MenuItem, MessageService } from 'primeng/api';
import { Routes } from '../../../../model/ShipmentRoutes';
import { Component } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { InternationalRoutes } from 'src/app/model/InternationalRoute';
import { LocationService } from 'src/app/page/location/service/location.service';
import { DatePipe } from '@angular/common';
// import { LocationPort } from 'src/app/model/LocationPort';
import { Location } from '../../../../model/Location';
import { LocationPortService } from 'src/app/page/location-port/service/location-port.service';

@Component({
  selector: 'app-update-international-road-routes',
  templateUrl: './update-international-road-routes.component.html',
  styleUrls: ['./update-international-road-routes.component.scss'],
  providers: [MessageService, DatePipe]
})
export class UpdateInternationalRoadRoutesComponent {

  items: MenuItem[] | undefined;
  type!:Type[];
  internationalRoute: InternationalRoutes = {
    id: null,
    destination: null,
    driverId: null,
    flight: null,
    type: null,
    eta: null,
    etd: null,
    origin: null,
    route: null,
    remarks: undefined
  }

  // location!: LocationPort[];

  routeNumbers: any;
  minETDDate: Date = new Date();
  // destination!: LocationPort[];
  routeId!:number;


  constructor(
    private internationalRouteService: InternationalRouteService,
    private locationPortService: LocationPortService,
    private messageService: MessageService,
    private datePipe: DatePipe,
    private router: Router,
    private route: ActivatedRoute) { }


  ngOnInit(): void {
    this.routeId = +this.route.snapshot.paramMap.get('id')!;
    this.items = [{ label: 'International Route List For Road', routerLink: '/international-routes-for-road' }, { label: 'Edit International Route For Road' }];
    this.getInternationalLocations();
    this.getInternationalRouteById(this.routeId);
    this.type=[{
      type:'Air'
    },{
      type:'Road'
    }]
  }

  getInternationalLocations() {
    // this.locationPortService.getAllLocationPort().subscribe((res: LocationPort[]) => {
    //   this.location = res.filter(el => el.status);
    // }, error => {
    //   if (error.error.body) {
    //     this.messageService.add({ severity: 'error', summary: 'Error', detail: error.error.body });
    //   } else {
    //     this.messageService.add({ severity: 'error', summary: 'Error', detail: error.error });
    //   }
    // })
  }

  onSubmit() {
    this.internationalRoute.etd = this.datePipe.transform(this.internationalRoute.etd, 'HH:mm:ss')
    this.internationalRoute.eta = this.datePipe.transform(this.internationalRoute.eta, 'HH:mm:ss')
    this.editInternationalRoutes(this.internationalRoute);
  }

  getInternationalRouteById(id:number){
    this.internationalRouteService.getInternationalRouteById(id).subscribe((res:InternationalRoutes)=>{
      this.internationalRoute=res;
      this.internationalRoute.etd = this.internationalRoute.etd ? new Date(`1970-01-01 ${this.internationalRoute.etd}`) : null;
      this.internationalRoute.eta = this.internationalRoute.eta ? new Date(`1970-01-01 ${this.internationalRoute.eta}`) : null;
      
    },(error:any)=>{
      this.messageService.add({ severity: 'error', summary: 'Error', detail: error.error.body });
    })
  }

  editInternationalRoutes(internationalRoute: InternationalRoutes) {
    
    this.internationalRouteService.updateInternationalRoute(this.routeId,internationalRoute).subscribe((res: InternationalRoutes) => {
      this.messageService.add({ severity: 'success', summary: 'Success', detail: 'International Route For Road Updated Successfully' });
      setTimeout(() => {
        this.router.navigate(['/international-routes-for-road']);
      }, 800);
    }, (error: any) => {
      if (error.error.body) {
        this.messageService.add({ severity: 'error', summary: 'Error', detail: error.error.body });
      } else {
        this.messageService.add({ severity: 'error', summary: 'Error', detail: error.error });
      }
    })
  }

  onETDDateSelected(selectedETDDate: Date) {
    const minETDDate = new Date(selectedETDDate);

    minETDDate.setDate(minETDDate.getDate() + 1);

    this.minETDDate = minETDDate;
  }

}

interface Type{
  type:string;
}
