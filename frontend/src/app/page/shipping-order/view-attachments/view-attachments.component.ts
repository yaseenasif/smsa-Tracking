import { Component } from '@angular/core';
import { MenuItem, MessageService } from 'primeng/api';
import { InternationalShippingService } from '../international/service/international-shipping.service';
import { ActivatedRoute } from '@angular/router';
import { DomesticShippingService } from '../domestic/service/domestic-shipping.service';
import * as FileSaver from 'file-saver';
import { saveAs } from 'file-saver';

@Component({
  selector: 'app-view-attachments',
  templateUrl: './view-attachments.component.html',
  styleUrls: ['./view-attachments.component.scss'],
  providers:[MessageService]
})
export class ViewAttachmentsComponent {
  items: MenuItem[] | undefined;
  fileMetaData!:any[];
  shipmentId!:number;
  shipmentType!:string;
  routeBy!:string;

  constructor(private internationalShippingService: InternationalShippingService,
    private domesticShippingService:DomesticShippingService,
    private messageService:MessageService,
    private route:ActivatedRoute){

  }

  ngOnInit() {
    this.shipmentType =this.route.snapshot.paramMap.get('name')!;
    this.shipmentId =+this.route.snapshot.paramMap.get('id')!;
    this.routeBy =this.route.snapshot.paramMap.get('through')!;

      this.items = this.internationalShippingService.dynamicLabel(this.routeBy)
      this.getFileMetaData(this.shipmentId);
  }


  getFileMetaData(id:number){
    if(this.shipmentType=='domesticShipment'){
      this.internationalShippingService.getFileMetaDataByDomesticShipment(id).subscribe((res:any)=>{
        this.fileMetaData=res;
      },(error:any)=>{
        console.log(error);
        this.messageService.add({ severity: 'error', summary: 'Error', detail: error.error.body });
      })
    }
    else if(this.shipmentType=='internationalShipment'){
      this.internationalShippingService.getFileMetaDataByInternationalShipment(id).subscribe((res:any)=>{
        this.fileMetaData=res;
      },(error:any)=>{
        console.log(error);
        this.messageService.add({ severity: 'error', summary: 'Error', detail: error.error.body });
      })
    }
  
  }
  downloadAttachment(url:string,fileName:string){
    this.domesticShippingService.downloadAttachments(fileName).subscribe(blob => saveAs(blob,fileName));
  }
 
}