#!/usr/bin/perl
use strict;
use warnings;
use POSIX;

sub order_custkey {
    int(rand() * 1_000_000);
}

sub rand_elem {
    @_[int(rand()*@_)];
}

sub order_status {
    rand_elem 'a'..'f';
}

sub return_flag {
    rand_elem 'w'..'z';
}

sub line_status {
    rand_elem 'g'..'l';
}

sub tax {
    rand_elem '7.00', '9.00', '12.00', '18.00';
}

sub discount {
    rand_elem '0.00', '1.00', '2.00', '4.00', '8.00', '10.00', '20.00', '30.00';
}

sub ship_instruct {
    rand_elem 'ship it', 'be careful', 'normal', 'livestock';
}

sub ship_mode {
    rand_elem 'truck', 'train', 'ship', 'plane';
}

sub order_prio {
    rand_elem '1'..'9';
}

sub order_clerk {
    sprintf "%s%03d", rand_elem('a'..'g'), int(rand()*1_000);
}

sub order_ship_prio {
    rand_elem '1'..'9';
}

sub order_date {
    time()-24*60*60*365+int(rand()*24*60*60*365);
}

sub ship_date {
    $_[0] + int(rand() * 24*60*60*30);
}

my $DATE_FMT = '%Y-%m-%d';
sub to_date_fmt {
    strftime $DATE_FMT, localtime($_[0]);
}

open my $lineitems, '>', 'lineitem.tbl' or die "Can't open lineitems.tbl to write: $!";
open my $orders, '>', 'orders.tbl' or die "Can't open orders.tbl to write: $!";
for my $order_key (1..1_000_000) {
    my $total_price = 0;
    my $order_date = order_date();
    for my $line_item (1..int(rand()*30+1)) {
        my $sup_key = int(rand()*2_000_000);
        my $extended_price = int(rand()*2_000_000)/100;
        my $tax = tax();
        my $discount = discount();
        my $quantity = int(rand()*100)+1;
        $total_price += $quantity * ($extended_price - ($extended_price * $discount / 100.0)) * (1.0+$tax/100.0);
        my $return_flag = return_flag();
        my $line_status = line_status();
        my $ship_date = ship_date($order_date);
        my $ship_date_fmted = to_date_fmt($ship_date);
        my $receipt_date = ship_date($ship_date);
        my $receipt_date_fmted = to_date_fmt($receipt_date);
        my $ship_instruct = ship_instruct();
        my $ship_mode = ship_mode();
        my $comment = 'no comment';
        print $lineitems "$order_key|$line_item|$sup_key|$line_item|$quantity|$extended_price|$discount|$tax|$return_flag|$line_status|$ship_date_fmted|$ship_date_fmted|$receipt_date_fmted|$ship_instruct|$ship_mode|$comment\n";
    }
    my @orders = (
        $order_key,
        order_custkey(),
        order_status(),
        sprintf("%.2f", $total_price),
        to_date_fmt($order_date),
        order_prio(),
        order_clerk(),
        order_ship_prio(),
        "comment.."
    );
    print $orders join('|', @orders) . "\n";
}
close $lineitems;
close $orders;
