#!/usr/bin/env escript

main(_) ->
  {ok, In} = file:read_file("../resources/input16_trivial.txt"),
  io:fwrite("~p~n", [do_stuff(In)]),
  {ok, In1} = file:read_file("../resources/input16.txt"),
  io:fwrite("~p~n", [do_stuff(In1)]).

do_stuff(In) ->
  In1 = binary:decode_hex(<<<<B>> || <<B:8>> <= In, B =/= 10>>),
  try
    {P, _} = parse(In1),
    {sum_versions(P), agg_values(P)}
  catch C:E:S -> io:fwrite("~p~n", [{C, E, S}]), erlang:exit(1) end.

parse(<<V:3, T:3, Rest/bitstring>>) ->
  {P, R} = case T of
             4 -> parse_literal(Rest, <<>>, 0);
             _ -> parse_operator(Rest)
           end,
  {{V, T, P}, R}.

sum_versions({V, 4, _}) ->
  V;
sum_versions({V, _, Sub}) ->
  V + lists:sum(lists:map(fun(P) -> sum_versions(P) end, Sub)).

agg_values({_, 4, V}) ->
  V;
agg_values({_, 0, Ps}) ->
  lists:sum(lists:map(fun(P) -> agg_values(P) end, Ps));
agg_values({_, 1, Ps}) ->
  lists:foldl(fun(P, Acc) -> Acc * agg_values(P) end, 1, Ps);
agg_values({_, 2, Ps}) ->
  lists:min(lists:map(fun(P) -> agg_values(P) end, Ps));
agg_values({_, 3, Ps}) ->
  lists:max(lists:map(fun(P) -> agg_values(P) end, Ps));
agg_values({_, 5, [P1, P2]}) ->
  case agg_values(P1) > agg_values(P2) of
    true -> 1;
    false -> 0
  end;
agg_values({_, 6, [P1, P2]}) ->
  case agg_values(P1) < agg_values(P2) of
    true -> 1;
    false -> 0
  end;
agg_values({_, 7, [P1, P2]}) ->
  case agg_values(P1) =:= agg_values(P2) of
    true -> 1;
    false -> 0
  end;
agg_values(_) ->
  ok.


parse_literal(<<T:1, X:4, Rest/bitstring>>, Acc, Nibbles) ->
  case T of
    1 ->
      parse_literal(Rest, <<Acc/bitstring, X:4>>, Nibbles + 1);
    0 ->
      Nibbles1 = Nibbles + 1,
      Bits = Nibbles1 * 4,
      <<Num:Bits>> = <<Acc/bitstring, X:4>>,
      {Num, Rest}
  end.

parse_operator(<<I:1, Rest/bitstring>>) ->
  IN = case I of
         0 -> 15;
         1 -> 11
       end,
  <<SubLen:IN, Rest1/bitstring>> = Rest,
  case I of
    0 ->
      <<Sub:SubLen/bitstring, Rest2/bitstring>> = Rest1,
      {parse_all(Sub, []), Rest2};
    1 ->
      parse_n(Rest1, [], SubLen)
  end.

parse_all(<<>>, Acc) ->
  lists:reverse(Acc);
parse_all(Bin, Acc) ->
  {Res, Rest} = parse(Bin),
  parse_all(Rest, [Res | Acc]).

parse_n(Rest, Acc, 0) ->
  {lists:reverse(Acc), Rest};
parse_n(Bin, Acc, N) ->
  {Res, Rest} = parse(Bin),
  parse_n(Rest, [Res | Acc], N - 1).
