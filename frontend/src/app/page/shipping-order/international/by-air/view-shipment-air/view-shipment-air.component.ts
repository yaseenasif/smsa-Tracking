import { Component } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { MenuItem, MessageService } from 'primeng/api';
import { InternationalShipment } from 'src/app/model/InternationalShipment';
import { Location } from 'src/app/model/Location'
import { DatePipe } from '@angular/common';
import { InternationalShippingService } from 'src/app/page/shipping-order/international/service/international-shipping.service';
import { ClipboardService } from 'ngx-clipboard';
import { SummaryService } from 'src/app/page/bounds/service/summary.service';

@Component({
  selector: 'app-view-shipment-air',
  templateUrl: './view-shipment-air.component.html',
  styleUrls: ['./view-shipment-air.component.scss'],
  providers: [MessageService, DatePipe]
})
export class ViewShipmentAirComponent {
  items: MenuItem[] | undefined;
  iSID!: number;
  resultArray:{overageAWBs:string|undefined,shortageAWBs:string|undefined,damageAWBs:string|undefined}[]=[];
  internationalShipment: InternationalShipment = {
    id: null,
    actualWeight: null,
    ata: null,
    attachments: null,
    carrier: null,
    destinationCountry: null,
    driverContact: null,
    driverName: null,
    flightNumber: null,
    numberOfBags: null,
    numberOfPallets: null,
    numberOfShipments: null,
    originCountry: null,
    overageAWBs: null,
    overages: null,
    preAlertNumber: null,
    received: null,
    referenceNumber: null,
    refrigeratedTruck: false,
    remarks: null,
    sealNumber: null,
    shipmentMode: null,
    shortageAWBs: null,
    shortages: null,
    status: null,
    tagNumber: null,
    totalShipments: null,
    type: 'By Air',
    vehicleNumber: null,
    vehicle: null,
    routeNumber: null,
    etd: null,
    eta: null,
    atd: null,
    trip: null,
    preAlertType: null,
    transitTimeTaken: null,
    originFacility: undefined,
    originLocation: undefined,
    destinationFacility: undefined,
    destinationLocation: undefined,
    numberOfBoxes: undefined,
    damage: null,
    damageAwbs: null,
    numberOfPalletsReceived: null,
    numberOfBagsReceived: null
  }

  selectedLocation!: Location;
  InternationalShipmentHistory:any;
  AnimationOveragesAWBs!: boolean;
  AnimationShortagesAWBs!: boolean;
  copyShortagesAWBs!: string;
  copyOveragesAWBs!: string;
  copyDamageAWBs!: string;
  AnimationDamageAWBs!: boolean;
  emailAttribute!: string;

  constructor(private router: Router,
    private _clipboardService: ClipboardService,
    private internationalShippingService: InternationalShippingService,
    private messageService: MessageService,
    private route: ActivatedRoute,
    private datePipe: DatePipe,
    private summaryService:SummaryService
  ) { }




  ngOnInit(): void {
    this.iSID = +this.route.snapshot.paramMap.get('id')!;
    this.getInternationalEmail(this.iSID);
    this.items = [{ label: 'International Outbound', routerLink: '/international-tile' }, { label: 'International Outbound By Air', routerLink: '/international-shipment-by-air' }, { label: 'View International Outbound By Air' }];

    this.getInternationalShipmentHistoryByInternationalShipmentId(this.iSID);
        // Now that you have the responses, you can proceed with the next steps
    this.getInternationalShipmentById(this.iSID);

  }

  getInternationalShipmentHistoryByInternationalShipmentId(id:number){
    this.internationalShippingService.getInternationalShipmentHistoryByInternationalShipmentId(id).subscribe((res:any)=>{
      this.InternationalShipmentHistory=res;
    },(error:any)=>{
      if(error.error.body){
        this.messageService.add({ severity: 'error', summary: 'Error', detail: error.error.body });
      }else{
        this.messageService.add({ severity: 'error', summary: 'Error', detail: error.error });
      }
    })
  }


  getInternationalShipmentById(id: number) {

    this.internationalShippingService.getInternationalShipmentByID(id).subscribe((res: InternationalShipment) => {
      this.internationalShipment = res;
      this.makeModelForTable();
    }, error => {
      this.messageService.add({ severity: 'error', summary: 'Error', detail: error.error.body });
    })
  }

  makeModelForTable() {
    let overagesAWBsArray:any;
    let shortagesAWBsArray:any;
    let damageAWBsArray:any;

    if(this.internationalShipment.overageAWBs!=null){
     overagesAWBsArray = this.internationalShipment.overageAWBs!.split(',');
     this.copyOveragesAWBs=overagesAWBsArray.join('\n')
    }else{
       overagesAWBsArray =[]
    }
    if(this.internationalShipment.shortageAWBs!=null){
     shortagesAWBsArray = this.internationalShipment.shortageAWBs!.split(',');
     this.copyShortagesAWBs=shortagesAWBsArray.join('\n')
    }else{
       shortagesAWBsArray =[]
    }
    if(this.internationalShipment.damageAwbs!=null){
      damageAWBsArray = this.internationalShipment.damageAwbs!.split(',');
      this.copyDamageAWBs=damageAWBsArray.join('\n')
     }else{
      damageAWBsArray =[]
     }



    // Determine the maximum length among the three arrays
    const maxLength = Math.max(
      overagesAWBsArray.length,
      shortagesAWBsArray.length,
      damageAWBsArray.length
    );

    // Create an array to store objects


    // Loop through the arrays to create objects
    for (let i = 0; i < maxLength; i++) {
      const obj: any = {};
      if (i < overagesAWBsArray.length) {
        obj.overageAWBs = overagesAWBsArray[i];
      }
      if (i < shortagesAWBsArray.length) {
        obj.shortageAWBs = shortagesAWBsArray[i];
      }
      if (i < damageAWBsArray.length) {
        obj.damageAWBs = damageAWBsArray[i];
      }
      this.resultArray.push(obj);
    }
  }
  onCopiedAnimationOveragesAWBs(){
    this.AnimationOveragesAWBs=true;
    this._clipboardService.copy(this.copyOveragesAWBs)
    setTimeout(() => {
    this.AnimationOveragesAWBs=false;
     }, 2000);
    }
    onCopiedAnimationDamageAWBs(){
      this.AnimationDamageAWBs=true;
      this._clipboardService.copy(this.copyDamageAWBs)
      setTimeout(() => {
      this.AnimationDamageAWBs=false;
       }, 2000);
      }

      onCopiedAnimationShortagesAWBs(){
        this.AnimationShortagesAWBs=true;
        this._clipboardService.copy(this.copyShortagesAWBs)
      setTimeout(() => {
        this.AnimationShortagesAWBs=false;
      }, 2000);
        }

        getInternationalEmail(id:number){
          this.summaryService.getInternationalEmail(id).subscribe((res)=>{
            this.emailAttribute='mailto:'.concat(res.to,'?cc=',res.cc,'?subject=',res.subject)
          },(error)=>{
            console.log(error);
          })
        }
        mintoHourMin(arg0: number) {
          return Math.trunc(arg0/60)+":"+ Math.trunc(arg0%60)
         }

}


